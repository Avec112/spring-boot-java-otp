package io.avec.otp;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@SpringBootApplication
public class JavaOtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaOtpApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {

        return args -> {
            final TimeBasedOneTimePasswordGenerator totp30 = new TimeBasedOneTimePasswordGenerator(); // 30 sec
            final TimeBasedOneTimePasswordGenerator totp15 = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(15));
            final Key key;
            {
                final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp30.getAlgorithm());

                // Key length should match the length of the HMAC output (160 bits for SHA-1, 256 bits
                // for SHA-256, and 512 bits for SHA-512).
                keyGenerator.init(160);

                key = keyGenerator.generateKey();
            }

            final Instant now = Instant.now();

            Duration timeStep = totp30.getTimeStep();
            log.debug("totp30 -> {} sec.", totp30.getTimeStep().getSeconds());
            log.debug("totp15 -> {} sec.", totp15.getTimeStep().getSeconds());

            int otp30 = totp30.generateOneTimePassword(key, now);
            int otp15 = totp15.generateOneTimePassword(key, now);

            while(true) {
                Thread.sleep(5000);

                Instant instant = Instant.now();
                if(otp30 == totp30.generateOneTimePassword(key, instant)) {
                    log.debug("Password otp30 is still valid");
                } else {
                    break;
                }

                if(otp15 == totp15.generateOneTimePassword(key, instant)) {
                    log.debug("Password otp15 is still valid");
                }

            }
            log.debug("Both passwords have expired.");

        };

    }


}
