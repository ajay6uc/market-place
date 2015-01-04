package com.marketplace.question;

import java.io.Serializable;
import java.util.HashMap;

public class ErrorObject implements Serializable{

	//common field in all node:::::::::::::::::::
		private String nodeType; 
		private String key;
		private String objectType;
		private String nid;
		private String domain;
		private String propType;
		private String contentType;
		private long createdOn;
		private String createdBy;
		
		//Common fields in all error nodes
		private String filePath;
		private String fileName;
		private short status;
		private long startTime;
		private String endTime;
		
		//CourseNid Field for populating node relationship
		private String courseNid;
		
		//fields in CourseFileLoadInfo node
		private String noOfRows;
		private String rowsInserted;
		private String rowsUpdated;
		private String rowsNotLoaded;
		
		//fields in QuestionFileLoadInfo node
		private String questionsInFile;
		private String questionsLoaded;
		private String questionsUpdated;
		private String questionsNotLoaded;
	
		private HashMap<String,QuestionFileErrorsInfo> questionFileErrorsContent = new HashMap<String, QuestionFileErrorsInfo>() ;
		
		public String getNodeType() {
			return nodeType;
		}
		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getObjectType() {
			return objectType;
		}
		public void setObjectType(String objectType) {
			this.objectType = objectType;
		}
		public String getNid() {
			return nid;
		}
		public void setNid(String nid) {
			this.nid = nid;
		}
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getPropType() {
			return propType;
		}
		public void setPropType(String propType) {
			this.propType = propType;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public long getCreatedOn() {
			return createdOn;
		}
		public void setCreatedOn(long createdOn) {
			this.createdOn = createdOn;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public short getStatus() {
			return status;
		}
		public void setStatus(short status) {
			this.status = status;
		}
		public String getQuestionsInFile() {
			return questionsInFile;
		}
		public void setQuestionsInFile(String questionsInFile) {
			this.questionsInFile = questionsInFile;
		}
		public String getQuestionsLoaded() {
			return questionsLoaded;
		}
		public void setQuestionsLoaded(String questionsLoaded) {
			this.questionsLoaded = questionsLoaded;
		}
		public String getQuestionsUpdated() {
			return questionsUpdated;
		}
		public void setQuestionsUpdated(String questionsUpdated) {
			this.questionsUpdated = questionsUpdated;
		}
		public String getQuestionsNotLoaded() {
			return questionsNotLoaded;
		}
		public void setQuestionsNotLoaded(String questionsNotLoaded) {
			this.questionsNotLoaded = questionsNotLoaded;
		}
		public String getNoOfRows() {
			return noOfRows;
		}
		public void setNoOfRows(String noOfRows) {
			this.noOfRows = noOfRows;
		}
		public String getRowsInserted() {
			return rowsInserted;
		}
		public void setRowsInserted(String rowsInserted) {
			this.rowsInserted = rowsInserted;
		}
		public String getRowsUpdated() {
			return rowsUpdated;
		}
		public void setRowsUpdated(String rowsUpdated) {
			this.rowsUpdated = rowsUpdated;
		}
		public String getRowsNotLoaded() {
			return rowsNotLoaded;
		}
		public void setRowsNotLoaded(String rowsNotLoaded) {
			this.rowsNotLoaded = rowsNotLoaded;
		}
		public long getStartTime() {
			return startTime;
		}
		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		
		public HashMap<String, QuestionFileErrorsInfo> getQuestionFileErrorsContent() {
			return questionFileErrorsContent;
		}
		public void setQuestionFileErrorsContent(
				HashMap<String, QuestionFileErrorsInfo> questionFileErrorsContent) {
			this.questionFileErrorsContent = questionFileErrorsContent;
		}
		public String getCourseNid() {
			return courseNid;
		}
		public void setCourseNid(String courseNid) {
			this.courseNid = courseNid;
		}
}
