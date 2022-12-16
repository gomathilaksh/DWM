package com.leovegas.walletService.exceptions;

/**
 * 
 * customException for walletService
 * @author gomathi lakshmanaperumal
 *
 */
public class CustomException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public CustomException(String message) {
		this.message = message;
	}
    public String getMessage() {
		return this.message;
    	
    }
}
