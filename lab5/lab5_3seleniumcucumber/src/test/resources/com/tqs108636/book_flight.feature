Feature: Booking a flight

    Scenario: Booking a flight from Mexico City to Buenos Aires
        When I navigate to "https://blazedemo.com/"
        And I select "Mexico City" as the departure city
        And I click Find Flights
        Then the page subsubheader should say "Flights from Mexico City to Buenos Aires:"
        And the page title should be "BlazeDemo - reserve"

        When I select the flight number 3
        Then the page subheader should say "Your flight from TLV to SFO has been reserved."
        And the page title should be "BlazeDemo Purchase"

        When I write my name as "user"
        And I write my "address" as "Braga"
        And I write my "city" as "Braga"
        And I write my "state" as "Braga"
        And I write my "zipCode" as "3770"
        And I write my "creditCardNumber" as "1234567890"
        And I write my "nameOnCard" as "User from Braga"
        And I click Purchase Flight
        Then the page header should say "Thank you for your purchase today!"