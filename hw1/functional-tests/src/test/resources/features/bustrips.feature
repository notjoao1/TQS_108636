@bus_trips
Feature: Reservations

  Scenario: User wants to make a reservation for a bus trip from Aveiro to Coimbra
    Given the user navigates to the home page
    When the user selects "Aveiro" as the departure city
    And the user selects "Coimbra" as the arrival city
    And the user selects the first trip 
    Then the page subheader should say "Reservation for a trip from Aveiro to Coimbra"

    When the user inputs "Pessoa Fixe" into his name
    And the user selects an available seat
    And the user clicks the Confirm Details button
    Then the page header should say "Thank you for using our services!"
    And the client name should be "Pessoa Fixe"

  Scenario: User wants to see the details of his reservation
    Given the user navigates to the reservation details page
    When the user inputs his reservation token equal to "3ba26311-323d-4d2d-b12d-d77dde16ca17"
    And the user clicks the Confirm Details button to confirm the token
    Then the reservation details should be shown
    And the reservation token should be "3ba26311-323d-4d2d-b12d-d77dde16ca17"
    And the found client name should be "Cliente Estatico Fixe"