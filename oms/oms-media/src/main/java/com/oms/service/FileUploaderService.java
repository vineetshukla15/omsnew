package com.oms.service;

import java.util.Collection;

import com.oms.viewobjects.Document;

public interface FileUploaderService {
	Document save(Document document);

	Collection<Document> save(Collection<Document> documents);

	Document update(Document document);

	Collection<Document> update(Collection<Document> documents);

	boolean delete(Document document);

	void delete(Collection<Document> documents);

}