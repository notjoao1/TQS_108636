package com.tqs108636;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
    private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Library library;

    private List<Book> queryResult;

    @Given("I have this list of books")
    public void setup(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        library = new Library();
        for (Map<String, String> book : rows) {
            library.addBook(new Book(book.get("title"), book.get("author"),
                    TestUtils.parseDateFromString(book.get("pub_date")), book.get("category")));
        }

        queryResult = null; // reset
    }

    @When("the customer searches for books published between {int} and {int}")
    public void customerYearIntervalQuery(int yearStart, int yearEnd) {
        log.info("Query Year Interval {}-{}", yearStart, yearEnd);
        queryResult = library.getBooksInYearInterval(yearStart, yearEnd);
    }

    @When("the customer searches for books written by {string}")
    public void customerSearchByAuthor(String author) {
        log.info("Query Author -> {}", author);
        queryResult = library.getBooksByAuthor(author);
    }

    @When("the customer searches for books with category {string}")
    public void customerSearchByCategory(String category) {
        log.info("Query Category -> {}", category);
        queryResult = library.getBooksByCategory(category);
    }

    @Then("{int} books should have been found")
    public void foundNBooks_FromQuery(int num_books) {
        assertEquals(queryResult.size(), num_books);
    }

    @And("Book {int} should have the title {string}")
    public void bookNumberN_HasTitle(int n, String title) {
        // 'n' indexes the queryResult array, but it starts on 1 instead of 0 as indexes
        // usually do
        assertEquals(queryResult.get(n - 1).getTitle(), title);
    }

    @And("all books should have {string} as author")
    public void authorQuery_ShouldReturnRightAuthor(String author) {
        assertTrue(queryResult.stream().allMatch((b) -> b.getAuthor().equals(author)));
    }

    @And("all books are written between {int} and {int}")
    public void booksWrittenInYearInterval(int yearStart, int yearEnd) {
        assertTrue(queryResult.stream()
                .allMatch((b) -> b.getPublicationDate().getYear() >= yearStart
                        && b.getPublicationDate().getYear() <= yearEnd));
    }
}
