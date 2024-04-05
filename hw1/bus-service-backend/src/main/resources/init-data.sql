-- DATABASE INITIALIZATION SCRIPT

INSERT INTO location (name) VALUES ('Braga');
INSERT INTO location (name) VALUES ('Aveiro');
INSERT INTO location (name) VALUES ('Porto');
INSERT INTO location (name) VALUES ('Coimbra');
INSERT INTO location (name) VALUES ('Bragança');

INSERT INTO route (total_distance_km) VALUES (120);
INSERT INTO route (total_distance_km) VALUES (70);
INSERT INTO route (total_distance_km) VALUES (230);
INSERT INTO route (total_distance_km) VALUES (125);
INSERT INTO route (total_distance_km) VALUES (60);
INSERT INTO route (total_distance_km) VALUES (170);

-- Aveiro -> Porto -> Braga
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 1, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 1, 1, 70);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 1, 2, 50);


-- Aveiro -> Porto
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 2, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 2, 1, 70);

-- Bragança -> Porto
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (5, 3, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 3, 1, 230);

-- Coimbra -> Aveiro -> Porto
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (4, 4, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 4, 1, 60);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 4, 2, 65);

-- Aveiro -> Coimbra
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 5, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (4, 5, 1, 60);

-- Braga -> Porto -> Aveiro -> Coimbra
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 6, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 6, 1, 50);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 6, 2, 120);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (4, 6, 3, 180);


INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1711316223), 15, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1711057023), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, FROM_UNIXTIME(1710279423), 8, 15);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, FROM_UNIXTIME(1712093823), 6, 10);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, FROM_UNIXTIME(1711967823), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, FROM_UNIXTIME(1711535823), 15, 15);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, FROM_UNIXTIME(1712093823), 20, 30);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, FROM_UNIXTIME(1711967823), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (4, FROM_UNIXTIME(1711967823), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (5, FROM_UNIXTIME(1711967823), 5, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (5, FROM_UNIXTIME(1709289423), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (5, FROM_UNIXTIME(1711967823), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (6, FROM_UNIXTIME(1714559823), 20, 40);

