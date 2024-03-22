# Lab 6

## SonarQube Docker Command

`$ docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest`

6.1 Token: `sqp_9db2eb488c20e9e36694b007ed9354e0b6ea081d` 
6.2 Token: `sqp_026f0069d8dc8016650d164aee8756d899a01b52`

## Lab 6.1 E)

- It passed the quality gate since the quality gate checks new code quality, and this is not new code.
- But the code shouldn't pass the quality gate, since the default quality gate defines these requirements:
    - New code has 0 issues
    - All new security hotspots are reviewed
    - New code is sufficiently covered by test - 80% or more
    - New code has limited duplication - 4% or less

- The code for the Euromillions project does not pass the first 3 requirements. It has 25 issues, only 73.5% coverage and has 1 security hotspot by using a cryptographically insecure random number generator.


## Lab 6.1 F)

| **Issue**     |**Problem Description**  | **How to Solve**  |
|---------------- | --------------- | --------------- |
| RNG Security Issue | Usage of `java.util.Random.Random()` as a random number generator. It is not cryptographically secure, and `java.security.SecureRandom` package should be used instead. It could pose a big risk if this was a real application and someone could predict the output of the Euromillions numbers :) | Use `java.security.SecureRandom` instead, which |
| Code smell (minor) | Returning `ArrayList` instead of the generic interface `List`. It's a maintainability issue. We should always return the generic interface instead of a specific implementation of it.  | Changing the return type from `ArrayList<type>` to `List<type>` |
| Code smell (major) | Concatenating strings that call methods inside of logger methods. These will be evaluated, even if the logging level is not met, which results in a performance loss. For example, when not running an app in debug mode, the concatenation would be evaluated. | Change concatenations to use supplier functions. For example, instead of `logger.log(Level.DEBUG, "Something went wrong: " + message);`, use `logger.log(Level.DEBUG, () -> "Something went wrong: " + message");`|

## Lab 6.2 A)

- Technical debt found - 1h46m worth
- It represents the cost (in terms of time) of solving all the issues listed, on average.

![Technical Debt](assets/technical_debt.png)

## Lab 6.2 B)

- After fixing the major and critical code smells, running the linter showed the following results:

![Issues After Fixes](assets/issues_after_fix.png)

- The number of issues went down, now all there is are minor and "info" code smells.