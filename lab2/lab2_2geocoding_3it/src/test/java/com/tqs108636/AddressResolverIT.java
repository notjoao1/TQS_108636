package com.tqs108636;

import com.tqs108636.connection.TqsBasicHttpClient;
import com.tqs108636.geocoding.Address;
import com.tqs108636.geocoding.AddressResolverService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AddressResolverIT {

    private AddressResolverService resolver;

    @BeforeEach
    void setup() {
        resolver = new AddressResolverService(new TqsBasicHttpClient());
    }

    @Test
    @DisplayName("Given a valid set of coordinates, returns corresponding Address object")
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        // will crash for now...need to set the resolver before using it
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        // return
        Address expected = new Address("Avenida da Universidade", "Aveiro", "3810-489", "");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());

    }

    @Test
    @DisplayName("Given bad coordinates, return 'no valid address")
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> result = resolver.findAddressForLocation(-361, -361);
        // verify no valid result
        assertFalse(result.isPresent());

    }
}
