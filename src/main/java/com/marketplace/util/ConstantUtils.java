package com.marketplace.util;

public class ConstantUtils {
	
	public static String QUESTIONS = "QUESTIONS";
	public static String FILE_UPLOAD = "BROWSER_FILE_UPLOAD_PROCESSS";
	public static String CONCEPT_ID = "conceptId";
	
	public  static int questionsinNotLoaded=0;
	public static long upLoadedOn = System.currentTimeMillis();
	public static final short ACTIVE_STATUS = 1;
	public static boolean fileLoadingStatus = true;
	public  static short questionStatus = 0;
	public static int questionsUpdated = 0;
	public static int questionsLoaded = 0;
	public static final String QUESTION_LEVEL_ERROR = "QUESTION_LEVEL_ERROR";
	public static final String FILE_LEVEL_ERROR = "File_Level_Error";
	public static final String FILL_IN_THE_BLANKS = "FILL_IN_THE_BLANKS";
	public static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";
	public static final String CONCEPT_CODE_IS_EMPTY = "Concept Code Is Empty";
	public static final String CONCEPT_CODE_NOT_FOUND_IN_DATABASE = "The Concept Code does not exist in Concept Node -- check related subject table";
	public static final String QUESTION_SERIAL_NUMBER_IS_EMPTY = "Question Serial Number is Empty";
	public static final String QUESTION_TEXT_IS_EMPTY = "Question Text is Empty";
	public static final String OPTION_IS_EMPTY = "Option is Empty";
	public static final String CORRECT_OPTION_IS_EMPTY = "Correct Option is Empty";
	public static final String COMPLEXITY_LEVEL_IS_EMPTY = "Complexity Level is Empty";
	public static final String CONCEPT_CODE_EXISTS_IN_DATABASE = "Concept code already exists in database";

	public static String removeUnwantedChars(String value){
		if(value != null){
			value = value.replaceAll("\\(\\r", ""); // Remove newline and return characters
			value = value.replaceAll("\\(\\n", ""); // Remove newline and return characters
			value = value.trim(); //remove start or end spaces
		}else{
			value = "";
		}
		return value;		
	}

}
