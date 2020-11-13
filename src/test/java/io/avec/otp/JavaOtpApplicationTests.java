package io.avec.otp;

import io.avec.otp.otp.Algorithm;
import io.avec.otp.otp.OneTimePasswordServiceException;
import io.avec.otp.otp.TimeBasedOneTimePassword;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class JavaOtpApplicationTests {

//    @Test
//    void contextLoads() {
//    }


    @Test
    void testPasswordTimeout() throws OneTimePasswordServiceException, InterruptedException {

        // we will have a short duration so we do not hold up the test for a long time

        // ARRANGE
        Duration duration = Duration.ofMillis(300); // give enough room for slow processing
        TimeBasedOneTimePassword otp = new TimeBasedOneTimePassword(duration, Algorithm.SHA1);

        // ACT
        int password = otp.getPassword();

        // ASSERT
        assertThat(password).isEqualTo(otp.getPassword()); // if this fails increase otp duration
        TimeUnit.MILLISECONDS.sleep(duration.toMillis()); // sleep past otp duration
        assertThat(password).isNotEqualTo(otp.getPassword());
    }


    @Test
    void testPasswordTimeout2() throws OneTimePasswordServiceException {

        // we will have a short duration so we do not hold up the test for a long time

        // ARRANGE
        Duration duration = Duration.ofMillis(300); // give enough room for slow processing
        TimeBasedOneTimePassword otp = new TimeBasedOneTimePassword(duration, Algorithm.SHA1);

        // ACT
        final int origPwd = otp.getPassword();
        int newPwd = otp.getPassword();
        System.out.println(origPwd + " == " + newPwd + ", for " + duration.toMillis()+ " milliseconds");

        // ASSERT
        while(origPwd == newPwd) {
            newPwd = otp.getPassword();
        }
        System.out.println(origPwd + " != " + newPwd);

    }

}
