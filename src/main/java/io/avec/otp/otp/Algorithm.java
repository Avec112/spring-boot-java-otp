package io.avec.otp.otp;

import lombok.Getter;

/**
 * In reality it is the key length we provide but the length defines the algorithm
 */
@Getter
public enum Algorithm {
    SHA1(160),
    SHA256(256),
    SHA512(512);
    private final int size;

    Algorithm(int size) {
        this.size = size;
    }
}
