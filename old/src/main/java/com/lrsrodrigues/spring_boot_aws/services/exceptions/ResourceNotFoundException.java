package com.lrsrodrigues.spring_boot_aws.services.exceptions;

public class ResourceNotFoundException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Object id) {
        super("Resource not found! Id " + id);
    }

}
