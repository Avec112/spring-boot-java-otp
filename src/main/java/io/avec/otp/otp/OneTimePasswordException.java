package io.avec.otp.otp;

import java.security.NoSuchAlgorithmException;

public class OneTimePasswordException extends Exception {
    public OneTimePasswordException(Exception e) {
        super(e);
    }
}
