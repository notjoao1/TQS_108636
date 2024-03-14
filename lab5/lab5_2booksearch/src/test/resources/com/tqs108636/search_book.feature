@search_book
Feature: Book search
    To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

    Background: List of books
        Given I have this list of books
            | title                  | author          | pub_date   | category |
            | One good book          | Anonymous       | 14-03-2013 | sci-fi   |
            | Some other book        | Tim Tomson      | 23-08-2014 | advice   |
            | How to cook a dino     | Fred Flintstone | 01-01-2012 | science  |
            | Tim Tomson is a writer | Tim Tomson      | 01-01-2024 | science  |

    Scenario: Search books by publication year
        When the customer searches for books published between 2013 and 2014
        Then 2 books should have been found
        And Book 1 should have the title 'One good book'
        And Book 2 should have the title 'Some other book'
        And all books are written between 2013 and 2014

    Scenario: Search books by author
        When the customer searches for books written by 'Tim Tomson'
        Then 2 books should have been found
        And Book 1 should have the title 'Some other book'
        And Book 2 should have the title 'Tim Tomson is a writer'
        And all books should have 'Tim Tomson' as author


    Scenario: Search books by category
        When the customer searches for books with category 'science'
        Then 2 books should have been found
        And Book 1 should have the title 'How to cook a dino'
        And Book 2 should have the title 'Tim Tomson is a writer'