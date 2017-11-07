package org.tavant.api.auth.model;

public enum Sources {
	INTERNAL_SOURCE("internal"),
	REGISTERED_SOURCE("register"),
	SOCIAL_SOURCE("source");
	
	String source;

	private Sources(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}
	


}
