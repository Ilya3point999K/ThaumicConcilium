package com.sasmaster.glelwjgl.java;

public class GLEException extends RuntimeException
{
    private static final String VERSION;
    
    public GLEException() {
    }
    
    public GLEException(final String message) {
        super(message);
    }
    
    static {
        VERSION = "$Revision: 1.1 $";
    }
}
