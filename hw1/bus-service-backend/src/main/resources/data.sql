-- DATABASE INITIALIZATION SCRIPT
-- 
-- Since I'm using an embbedded DB, we need to seed it whenever the application starts up

INSERT INTO Location (name) VALUES ('Braga');
INSERT INTO Location (name) VALUES ('Aveiro');
INSERT INTO Location (name) VALUES ('Porto');
INSERT INTO Location (name) VALUES ('Coimbra');
INSERT INTO Location (name) VALUES ('Bragança');

INSERT INTO Route (total_distance_km) VALUES (120);
INSERT INTO Route (total_distance_km) VALUES (70);
INSERT INTO Route (total_distance_km) VALUES (230);
INSERT INTO Route (total_distance_km) VALUES (125);
INSERT INTO Route (total_distance_km) VALUES (60);
INSERT INTO Route (total_distance_km) VALUES (170);

-- Aveiro -> Porto -> Braga
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 1, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 1, 1, 70);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 1, 2, 50);


-- Aveiro -> Porto
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 2, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 2, 1, 70);

-- Bragança -> Porto
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (5, 3, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 3, 1, 230);

-- Coimbra -> Aveiro -> Porto
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (4, 4, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 4, 1, 60);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 4, 2, 65);

-- Aveiro -> Coimbra
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 5, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (4, 5, 1, 60);

-- Braga -> Porto -> Aveiro -> Coimbra
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 6, 0, 0);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 6, 1, 50);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 6, 2, 120);
INSERT INTO Route_Stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (4, 6, 3, 180);


INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1711316223, DATE '1970-01-01'), 15, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1711057023, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, DATEADD('SECOND', 1710279423, DATE '1970-01-01'), 8, 15);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, DATEADD('SECOND', 1712093823, DATE '1970-01-01'), 6, 10);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, DATEADD('SECOND', 1711967823, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, DATEADD('SECOND', 1711535823, DATE '1970-01-01'), 15, 15);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, DATEADD('SECOND', 1712093823, DATE '1970-01-01'), 20, 30);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, DATEADD('SECOND', 1711967823, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (4, DATEADD('SECOND', 1711967823, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (5, DATEADD('SECOND', 1711967823, DATE '1970-01-01'), 5, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (5, DATEADD('SECOND', 1709289423, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (5, DATEADD('SECOND', 1711967823, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (6, DATEADD('SECOND', 1714559823, DATE '1970-01-01'), 20, 40);

