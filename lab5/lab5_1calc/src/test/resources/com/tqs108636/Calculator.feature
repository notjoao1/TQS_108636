# mvn test -Dcucumber.filter.tags="@calc_sample" -> only tests this feature
@calc_sample
Feature: Basic Arithmetics

    Arithmetics using reverse polish notation

    Background: A calculator
        Given a calculator I just turned on

    Scenario: Addition
        When I add 4 and 5
        Then the result is 9

    Scenario: Subtraction
        When I subtract 4 to 7
        Then the result is -3

    Scenario Outline: Several Additions
        When I add <a> and <b>
        Then the result is <c>

        Examples:
            | a  | b | c |
            | 1  | 2 | 3 |
            | -2 | 3 | 1 |

    Scenario Outline: Several Multiplications
        When I multiply <x> and <y>
        Then the result is <z>

        Examples:
            | x  | y | z  |
            | 1  | 1 | 1  |
            | -1 | 1 | -1 |
            | -3 | 2 | -6 |
            | 4  | 4 | 16 |
            | 5  | 0 | 0  |

    Scenario Outline: Several Divisions
        When I divide <x> by <y>
        Then the result is <z>

        Examples:
            | x  | y  | z   |
            | 2  | 2  | 1   |
            | 2  | -2 | -1  |
            | 5  | 2  | 2.5 |
            | 0  | 4  | 0   |
            | -2 | -1 | 2   |

    Scenario: Invalid Division
        When I divide 4 by 0
        Then an IllegalArgumentException is thrown