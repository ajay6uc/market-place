package com.marketplace.question;


import java.util.HashMap;

public class QuestionFileLoadInfo extends ErrorObject{
	 //ignore field of common::::::::::::::::::::::::::::::::::::::::::::::::::::
		private String nodeType; 
		private String key;
		private String objectType;
		private String domain;
		private String propType;
		private String contentType;
		private String courseNid;
		
		/*******for RedisQuestionFileLoadInfo***********/
		private String noOfRows;
		private String rowsInserted;
		private String rowsUpdated;
		private String rowsNotLoaded;
		private HashMap<String,QuestionFileErrorsInfo> questionFileErrorsContent ;
}
