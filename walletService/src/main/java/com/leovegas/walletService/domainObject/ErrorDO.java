package com.leovegas.walletService.domainObject;

import java.util.Date;
import java.util.List;

public class ErrorDO {

	private String status;
	private String errorMessage;
	private Date timeStamp;
	private List<String> errorMessageList;
	
	public ErrorDO(String status, String errorMessage, Date timeStamp, List<String>errorMessageList) {
		this.status = status;
		this.errorMessage = errorMessage;
		this.timeStamp = timeStamp;
		this.errorMessageList = errorMessageList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<String> getErrorMessageList() {
		return errorMessageList;
	}

	public void setErrorMessageList(List<String> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

}
