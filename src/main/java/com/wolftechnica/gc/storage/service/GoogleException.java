package com.wolftechnica.gc.storage.service;

/**
 * The FacebookException wraps all checked standard Java exception and enriches
 * them with a custom error code. You can use this code to retrieve localized
 * error messages and to link to our online documentation.
 * 
 * @author Hem Chand
 */
public class GoogleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7718828512143293558L;

	private GoogleExceptionCodes exceptionCodes;

	public GoogleException() {
		super();

	}

	public GoogleException(GoogleExceptionCodes exceptionCodes) {
		super();
		this.setExceptionCodes(exceptionCodes);
	} 

	public GoogleExceptionCodes getExceptionCodes() {
		return exceptionCodes;
	}

	public void setExceptionCodes(GoogleExceptionCodes exceptionCodes) {
		this.exceptionCodes = exceptionCodes;
	}

}
