package com.tqs108636.busservicebackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs108636.busservicebackend.model.Location;
import com.tqs108636.busservicebackend.model.Route;
import com.tqs108636.busservicebackend.model.RouteStop;

@DataJpaTest
class RouteRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RouteRepository routeRepository;

    Location locAveiro, locPorto, locBraga, locFaro;
    RouteStop rs1, rs2, rs3, rs4, rs5, rs6, rs7;
    Route route1, route2, route3;

    @BeforeEach
    void setup() {
        // route 1: Aveiro -> Porto -> Braga
        // route 2: Braga -> Porto
        route1 = new Route(70);
        route2 = new Route(30);
        route3 = new Route(60);

        locAveiro = new Location("Aveiro");
        locPorto = new Location("Porto");
        locBraga = new Location("Braga");
        locFaro = new Location("Faro");

        rs1 = new RouteStop(locAveiro, route1, 0, 0);
        rs2 = new RouteStop(locPorto, route1, 1, 40);
        rs3 = new RouteStop(locBraga, route1, 2, 30);

        rs4 = new RouteStop(locPorto, route2, 0, 0);
        rs5 = new RouteStop(locBraga, route2, 1, 30);

        rs6 = new RouteStop(locAveiro, route3, 0, 0);
        rs7 = new RouteStop(locBraga, route3, 1, 60);

        entityManager.persist(locAveiro);
        entityManager.persist(locPorto);
        entityManager.persist(locBraga);
        entityManager.persist(locFaro);

        entityManager.persist(route1);
        entityManager.persist(route2);
        entityManager.persist(route3);

        entityManager.persist(rs1);
        entityManager.persist(rs2);
        entityManager.persist(rs3);
        entityManager.persist(rs4);
        entityManager.persist(rs5);
        entityManager.persist(rs6);
        entityManager.persist(rs7);

        entityManager.flush();
    }

    @Test
    void testFindStartingWithLocationAveiro() {
        List<Route> routesStartingAveiro = routeRepository.findRoutesByStartingLocation(locAveiro);

        assertEquals(2, routesStartingAveiro.size());
        assertTrue(routesStartingAveiro.contains(route1));
        assertTrue(routesStartingAveiro.contains(route3));
    }

    @Test
    void testFindStartingWithInvalidLocation() {
        List<Route> routesStartingFaro = routeRepository.findRoutesByStartingLocation(locFaro);

        assertTrue(routesStartingFaro.isEmpty());
    }

    @Test
    void testFindByStopLocationBraga() {
        List<Route> routesWithBraga = routeRepository.findByStopLocation(locBraga);

        assertEquals(3, routesWithBraga.size());
        assertTrue(routesWithBraga.contains(route1));
        assertTrue(routesWithBraga.contains(route2));
        assertTrue(routesWithBraga.contains(route3));
    }

    @Test
    void testFindByStopLocationFaro() {
        List<Route> routesWithFaro = routeRepository.findByStopLocation(locFaro);

        assertTrue(routesWithFaro.isEmpty());
    }

}
