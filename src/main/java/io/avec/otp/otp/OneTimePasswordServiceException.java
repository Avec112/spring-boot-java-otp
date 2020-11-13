package io.avec.otp.otp;

import java.security.NoSuchAlgorithmException;

public class OneTimePasswordServiceException extends Exception {
    public OneTimePasswordServiceException(Exception e) {
        super(e);
    }
}
