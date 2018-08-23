package com.wolftechnica.gc.storage.service.core;

public enum GoogleExceptionCodes {

	UNABLE_TO_CONNECT(70001, "unable to connect to gcp."),
	NO_BUCKET_FOUND(70002, "No bucket found with given name."),
	NO_BUCKET_OBJECT_FOUND(70003, "No bucket object found with given name.")

	;

	private GoogleExceptionCodes(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	/** The exception code. */
	private final Integer code;

	/** The exception description. */
	private final String description;

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
