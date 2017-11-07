package com.oms.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.http.HttpStatus;

import com.api.email.exceptions.OMSSystemException;
import com.oms.commons.utils.FileUtil;
import com.oms.exceptions.Status;
import com.oms.service.FileUploaderService;
import com.oms.viewobjects.Document;

public class FileSystemFileUploaderService implements FileUploaderService {

	private Path uploadDir;

	public FileSystemFileUploaderService(final String uploadDir) {
		this.uploadDir = Paths.get(uploadDir);
		init();
	}

	@Override
	public Document save(Document document) {
		return this.saveDocument(document);
	}

	@Override
	public Collection<Document> save(Collection<Document> documents) {
		documents.forEach(this::save);
		return documents;
	}

	@Override
	public Document update(Document document) {
		return this.save(document);
	}

	@Override
	public Collection<Document> update(Collection<Document> documents) {
		documents.forEach(this::update);
		return documents;
	}

	@Override
	public boolean delete(Document document) {
		try {
			return FileUtil.delete(Paths.get(document.getPath()));
		} catch (IOException ioe) {
			handleError(ioe);
		}
		return false;
	}

	@Override
	public void delete(Collection<Document> documents) {
		documents.forEach(this::delete);
	}
	
	public void init() {
		if (!Files.exists(this.uploadDir)) {
			try {
				Files.createDirectories(this.uploadDir);
			} catch (IOException e) {
				throw new RuntimeException("Error occurred while creating file upload directory");
			}
		}
	}
	
	private Path absoluteFilePath(Document document) {
		return this.uploadDir.resolve(this.effectiveFilename(document)).toAbsolutePath();
	}

	private String effectiveFilename(Document document) {
		return new StringBuilder(FileUtil.getBaseName(document.getName())).append("-").append(document.getLinkedWith())
				.append(".").append(FileUtil.getExtension(document.getName())).toString();
	}

	private void handleError(IOException ioe) {
		throw new OMSSystemException(Status.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR,
				"Error occurred while uploading/updating document(s)");
	}

	private Document saveDocument(Document document) {
		final Path path = this.absoluteFilePath(document);
		try {
			FileUtil.save(path, document.contentStream());
		} catch (IOException ioe) {
			handleError(ioe);
		}
		document.setPath(path.toFile().getAbsolutePath());
		return document;
	}

}
