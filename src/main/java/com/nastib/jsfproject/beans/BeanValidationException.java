
package com.nastib.jsfproject.beans;

public class BeanValidationException  extends Exception {
    private static final long serialVersionUID = 1L;

    public BeanValidationException( String message ) {
        super( message );
    }
}