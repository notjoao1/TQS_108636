package com.tqs108636;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockPortfolioTest {

    @Mock
    private IStockMarketService stockmarket;

    @InjectMocks
    private StocksPortfolio stocksPortfolio;

    @Test
    void testTotalValue() {
        // 1. Prepare a mock to substitute the remote stockmarket service (@Mock
        // annotation)
        // 2. Create an instance of the subject under test (SuT) and use the mock to set
        // the (remote) service instance.

        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(stockmarket.lookUpPrice("EBAY")).thenReturn(4.0);
        when(stockmarket.lookUpPrice("MSFT")).thenReturn(1.5);

        // If we add excessive expectations, it throws an exception
        // (UnnecessaryStubbingException)

        // 4. Execute the test (use the service in the SuT)
        stocksPortfolio.addStock(new Stock("EBAY", 2));
        stocksPortfolio.addStock(new Stock("MSFT", 2));

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(stocksPortfolio.totalValue(), 11.0);
        // verify is useful when we wanna know if a method was called N times
        verify(stockmarket, times(2)).lookUpPrice(anyString());
    }
}
