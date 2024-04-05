-- DATABASE INITIALIZATION SCRIPT
-- 
-- Since I'm using an embbedded DB, we need to seed it whenever the application starts up

INSERT INTO location (name) VALUES ('Aveiro');
INSERT INTO location (name) VALUES ('Porto');
INSERT INTO location (name) VALUES ('Braga');
INSERT INTO location (name) VALUES ('Faro');

INSERT INTO route (total_distance_km) VALUES (70);
INSERT INTO route (total_distance_km) VALUES (30);
INSERT INTO route (total_distance_km) VALUES (60);


-- Aveiro -> Porto -> Braga
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 1, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 1, 1, 40);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 1, 2, 30);


-- Porto -> Braga
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (2, 2, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 2, 1, 30);

-- Aveiro -> Braga
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (1, 3, 0, 0);
INSERT INTO route_stop (location_id, route_id, stop_number, distance_km_from_last_stop)
    VALUES (3, 3, 1, 60);


INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1719316223), 15, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1711057023), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (2, FROM_UNIXTIME(1713279423), 8, 15);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1713693823), 6, 10);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1719967823), 10, 20);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1710535823), 15, 15);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, FROM_UNIXTIME(1718093823), 20, 30);
INSERT INTO trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, FROM_UNIXTIME(1714967823), 10, 20);


-- Trip 1 - seats 1 and 6 taken
-- Trip 2 - seat 0 taken
-- Trip 3 - seats 4 and 7 taken
-- Trip 4 - seat 5 taken
-- Trip 5 - seat 2 taken
-- Trip 6 - seat 10 taken
-- Trip 7 - seat 9 taken
-- Trip 8 - seat 3 taken
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('3ba26311323d4d2db12dd77dde16ca17'), 3, 7, 'Client A'); 
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('750c6aa9d0eb4c22873fe769619452e7'), 1, 1, 'Client B');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('9ab862f85cc74deab12feb9274c1be52'), 7, 9, 'Client C');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('5c90d66239de43afaf8639d916154085'), 4, 5, 'Client D');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('9c50c8550efb41b18c410f0e44aefb8a'), 2, 0, 'Client E');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('3a848b635f3d4045a54bf324942920c9'), 8, 3, 'Client F');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('e1e1a58ecd3b446e96b4cc2cf67bff76'), 6, 10, 'Client G');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('3afbe2e627194be395257983f6ffc2f1'), 5, 2, 'Client H');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('1b4cad3782724be8a39f56061cd114f1'), 1, 6, 'Client I');
INSERT INTO reservation (id, trip_id, seat_number, client_name)
    VALUES (UNHEX('08857f19a8a6499f94dfe24d50c7b9dd'), 3, 4, 'Client J'); 
