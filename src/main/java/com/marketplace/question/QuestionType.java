package com.marketplace.question;

public enum QuestionType {
	
	FILL_IN_THE_BLANKS((short)1), MULTIPLE_CHOICE((short)2), SINGLE_CHOICE((short)3);

	    private short value;

	    private QuestionType(short value)
	    {
	            this.value = value;
	    }

	    public short getValue() 
	    {
	            return value;
	    }

	    public static QuestionType getEnumByValue(short value)
	    {
	        switch (value)
	        {
	            case 1:
	                return FILL_IN_THE_BLANKS;
	            case 2:
	                return MULTIPLE_CHOICE;
	            default:
	                return SINGLE_CHOICE;
	        }
	    }
}
