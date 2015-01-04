package com.marketplace.question;

public enum ComplexityLevel {
	
	L1((short)1), L2((short)2), L3((short)3), L4((short)4);

	    private short value;

	    private ComplexityLevel(short value)
	    {
	            this.value = value;
	    }

	    public short getValue() 
	    {
	            return value;
	    }

	    public static ComplexityLevel getEnumByValue(short value)
	    {
	        switch (value)
	        {
	            case 1:
	                return L1;
	            case 2:
	                return L2;
	            case 3:
	                return L3;
	            case 4:
	                return L4;
	            default:
	                return L1;
	        }
	    }
}
