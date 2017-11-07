package com.oms.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {

	public static String getBaseName(final String fileName) {
		return FilenameUtils.getBaseName(fileName);
	}

	public static String getExtension(final String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

	public static void save(Path path, InputStream content) throws IOException {
		Files.copy(content, path, StandardCopyOption.REPLACE_EXISTING);
	}

	public static boolean delete(Path path) throws IOException {
		return Files.deleteIfExists(path);
	}

	public static void copyFile(String srcPath, String destPath) throws IOException {
		Files.copy(Paths.get(srcPath), Paths.get(destPath));
	}

	public static String mimeType(String filePath) throws IOException {
		String contentType = Files.probeContentType(Paths.get(filePath));
		return contentType != null ? contentType : "application/octet-stream";
	}

	public static byte[] getContent(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

	public static InputStream contentAsStream(String path) throws IOException {
		return new ByteArrayInputStream(Files.readAllBytes(Paths.get(path)));
	}

}
