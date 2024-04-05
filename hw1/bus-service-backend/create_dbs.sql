-- Creates main DB and test DB and gives the user privileges to use it

CREATE DATABASE testbusdb;
CREATE DATABASE busdb;

GRANT ALL PRIVILEGES ON testbusdb.* TO 'test'@'%'; 
GRANT ALL PRIVILEGES ON busdb.* TO 'test'@'%'; 