package com.marketplace.question;

import java.io.Serializable;

public class QuestionFileErrorsInfo implements Serializable {
	private String id;
	private String loadInfoId;
	private int rowNumber;
	private String errorType;
	private String errorDesc;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoadInfoId() {
		return loadInfoId;
	}
	public void setLoadInfoId(String loadInfoId) {
		this.loadInfoId = loadInfoId;
	}
	
	
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}