INSERT INTO hotels  SELECT * FROM CSVREAD('classpath:hoteldb.csv');