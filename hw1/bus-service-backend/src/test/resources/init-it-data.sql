-- DATABASE INITIALIZATION SCRIPT
-- 
-- Since I'm using an embbedded DB, we need to seed it whenever the application starts up

INSERT INTO Location (name) VALUES ('Aveiro');
INSERT INTO Location (name) VALUES ('Porto');
INSERT INTO Location (name) VALUES ('Braga');
INSERT INTO Location (name) VALUES ('Faro');

INSERT INTO Route (total_distance_km) VALUES (70);
INSERT INTO Route (total_distance_km) VALUES (30);
INSERT INTO Route (total_distance_km) VALUES (60);


-- Aveiro -> Porto -> Braga
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 1, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 1, 1, 40);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 1, 2, 30);


-- Porto -> Braga
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 2, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 2, 1, 30);

-- Aveiro -> Braga
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 3, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 3, 1, 60);


INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1719316223, DATE '1970-01-01'), 15, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1711057023, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, DATEADD('SECOND', 1713279423, DATE '1970-01-01'), 8, 15);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1712693823, DATE '1970-01-01'), 6, 10);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1711967823, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1710535823, DATE '1970-01-01'), 15, 15);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1712093823, DATE '1970-01-01'), 20, 30);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, DATEADD('SECOND', 1714967823, DATE '1970-01-01'), 10, 20);
