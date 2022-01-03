
# Java TOTP
Time Based One Time Password.\
Simple implementation of [jchambers/java-otp](https://github.com/jchambers/java-otp)

## Requirements
* Java 8+
* Maven 3.3+ (older might work)


## Dependency
```xml
<dependency>
  <groupId>com.eatthepath</groupId>
  <artifactId>java-otp</artifactId>
  <version>0.2.0</version>
</dependency>
```

## Classes
There are two classes in use
* `TimeBasedOneTimePassword`
* `Algorithm`

There is also an exception wrapper class `OneTimePasswordException` but exceptions would probably never occur

## Implementation
```java
Duration duration = Duration.ofMinutes(5); // valid for 5 minutes
TimeBasedOneTimePassword totp = new TimeBasedOneTimePassword(duration); 
int password = totp.getPassword(); 

if(password == totp.getPassword()) { 
    // same password
} 

// sleep past otp duration
TimeUnit.MILLISECONDS.sleep(duration.toMillis()); 

if(password != totp.getPassword()) { 
    // different password
}

```
The password will be valid for the next 5 minutes.    
I have not tested for a max duration limit.

## Usage

```
> mvn test
```

The output should contains something like this
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running io.avec.otp.JavaOtpApplicationTests
154900 == 154900, for 300 milliseconds
154900 != 269168
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.49 s - in io.avec.otp.JavaOtpApplicationTests

```


