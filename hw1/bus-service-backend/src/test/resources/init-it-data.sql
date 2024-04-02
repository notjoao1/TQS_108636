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
    VALUES (1, DATEADD('SECOND', 1713693823, DATE '1970-01-01'), 6, 10);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1719967823, DATE '1970-01-01'), 10, 20);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1710535823, DATE '1970-01-01'), 15, 15);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (1, DATEADD('SECOND', 1718093823, DATE '1970-01-01'), 20, 30);
INSERT INTO Trip (route_id, departure_time, price_euro, number_of_seats)
    VALUES (3, DATEADD('SECOND', 1714967823, DATE '1970-01-01'), 10, 20);


-- Trip 1 - seats 1 and 6 taken
-- Trip 2 - seat 0 taken
-- Trip 3 - seats 4 and 7 taken
-- Trip 4 - seat 5 taken
-- Trip 5 - seat 2 taken
-- Trip 6 - seat 10 taken
-- Trip 7 - seat 9 taken
-- Trip 8 - seat 3 taken
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('3ba26311-323d-4d2d-b12d-d77dde16ca17', 3, 7, 'Client A'); 
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('750c6aa9-d0eb-4c22-873f-e769619452e7', 1, 1, 'Client B');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('9ab862f8-5cc7-4dea-b12f-eb9274c1be52', 7, 9, 'Client C');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('5c90d662-39de-43af-af86-39d916154085', 4, 5, 'Client D');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('9c50c855-0efb-41b1-8c41-0f0e44aefb8a', 2, 0, 'Client E');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('3a848b63-5f3d-4045-a54b-f324942920c9', 8, 3, 'Client F');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('e1e1a58e-cd3b-446e-96b4-cc2cf67bff76', 6, 10, 'Client G');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('3afbe2e6-2719-4be3-9525-7983f6ffc2f1', 5, 2, 'Client H');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('1b4cad37-8272-4be8-a39f-56061cd114f1', 1, 6, 'Client I');
INSERT INTO Reservation (id, trip_id, seat_number, client_name)
    VALUES ('08857f19-a8a6-499f-94df-e24d50c7b9dd', 3, 4, 'Client J'); 
