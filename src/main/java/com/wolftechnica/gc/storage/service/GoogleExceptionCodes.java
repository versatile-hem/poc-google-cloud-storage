package com.wolftechnica.gc.storage.service;

public enum GoogleExceptionCodes {

	UNABLE_TO_CONNECT(10001, "unable to connect to faceboook.")

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
