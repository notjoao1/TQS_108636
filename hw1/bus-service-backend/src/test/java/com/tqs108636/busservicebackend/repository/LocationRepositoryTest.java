package com.tqs108636.busservicebackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs108636.busservicebackend.model.Location;

@DataJpaTest
class LocationRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LocationRepository locationRepository;

    @Test
    void testFindByName() {
        Location loc1 = new Location("Test1");
        Location loc2 = new Location("Test2");
        entityManager.persistAndFlush(loc1);
        entityManager.persistAndFlush(loc2);

        assertEquals(Optional.of(loc1), locationRepository.findByName("Test1"));
    }

    @Test
    void testFindByInvalidName() {
        Location loc1 = new Location("Test1");
        Location loc2 = new Location("Test2");
        entityManager.persistAndFlush(loc1);
        entityManager.persistAndFlush(loc2);

        assertEquals(Optional.empty(), locationRepository.findByName("braga"));
    }
}
