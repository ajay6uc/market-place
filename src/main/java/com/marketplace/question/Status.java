package com.marketplace.question;

public enum Status {
	
	Active((short)1), InActive((short)2), Review((short)3);

	    private short value;

	    private Status(short value)
	    {
	            this.value = value;
	    }

	    public short getValue() 
	    {
	            return value;
	    }

	    public static Status getEnumByValue(short value)
	    {
	        switch (value)
	        {
	        	case 1:
	        		return Active;
	        	case 2:
	                return InActive;
	            case 3:
	                return Review;
	            default:
	                return Active;
	        }
	    }
}
