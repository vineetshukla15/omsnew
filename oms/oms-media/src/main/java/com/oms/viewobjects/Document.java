package com.oms.viewobjects;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Document {
	private String name;
	private String contentType;
	private String path;
	private byte[] content;
	private String linkedWith;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getLinkedWith() {
		return linkedWith;
	}

	public void setLinkedWith(String linkedWith) {
		this.linkedWith = linkedWith;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public InputStream contentStream() {
		return new ByteArrayInputStream(this.content);
	}
	
}
