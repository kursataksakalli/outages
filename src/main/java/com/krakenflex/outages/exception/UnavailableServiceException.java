package com.krakenflex.outages.exception;

/**
 * exception for the unavailable services
 */
public class UnavailableServiceException extends RuntimeException {
    public UnavailableServiceException() {
    }

    public UnavailableServiceException(String message) {
        super(message);
    }
}
