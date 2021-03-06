package io.avec.otp.otp;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

/**
 * This class is a wrapper over TimeBasedOneTimePasswordGenerator
 * encapsulating generator, current time (Instant.now()) and key
 * The class will return a new password with the same
 */
public class TimeBasedOneTimePassword {
    private final TimeBasedOneTimePasswordGenerator otp;
    private final Key key;

    /**
     * Initializes TimeBasedOneTimePasswordGenerator and Key
     * Defaults to SHA1 algorithm.
     * @param duration How long the password is valid
     * @throws OneTimePasswordException An Exception wrapper
     */
    public TimeBasedOneTimePassword(Duration duration) throws OneTimePasswordException {
        this(duration, Algorithm.SHA1);
    }

    /**
     * Initializes TimeBasedOneTimePasswordGenerator and Key
     * @param duration How long the password is valid
     * @param algorithm SHA1(160), SHA256(256) or SHA512(512)
     * @throws OneTimePasswordException An Exception wrapper
     */
    public TimeBasedOneTimePassword(Duration duration, Algorithm algorithm) throws OneTimePasswordException {
        try {
            otp = new TimeBasedOneTimePasswordGenerator(duration);
            key = getKey(algorithm.getSize(), otp.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new OneTimePasswordException(e);
        }
    }



    /**
     * Returnes a time based password
     * @return a six figure numbered password
     * @throws OneTimePasswordException An Exception wrapper
     */
    public int getPassword() throws OneTimePasswordException {
        try {
            final Instant now = Instant.now();
            return otp.generateOneTimePassword(key, now);
        } catch (InvalidKeyException e) {
            throw new OneTimePasswordException(e);
        }
    }

    private Key getKey(int size, String algorithm) throws NoSuchAlgorithmException {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(size);
        return keyGenerator.generateKey();
    }
}
