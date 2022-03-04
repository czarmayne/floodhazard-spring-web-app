INSERT INTO role VALUES (1,'ADMIN');

INSERT INTO sensitivity_condition VALUES (1,sysdate,'RED','Not passable by small vehicles and not passable by children and Adult and Violent flood');
INSERT INTO sensitivity_condition VALUES (2,sysdate,'ORANGE','Below knee flood in 2 cosecutive hours and 4 cosecutive hours is above knee flood - Not passable by small vehicles and not passable by Children and Adult');
INSERT INTO sensitivity_condition VALUES (3,sysdate,'YELLOW','Below knee flood in 4 hours');
INSERT INTO sensitivity_condition VALUES (4,sysdate,'GREEN','Moderate Rainfall');

INSERT INTO rain_type VALUES (1,sysdate,2.5,0,'Light Rain','when the precipitation rate is < 2.5 mm (0.098 in) per hour');
INSERT INTO rain_type VALUES (2,sysdate,7.6,2.5,'Moderate Rain','when the precipitation rate is between 2.5 mm (0.098 in) - 7.6 mm (0.30 in) or 10 mm (0.39 in) per hour');
INSERT INTO rain_type VALUES (3,sysdate,50,7.6,'Heavy Rain','when the precipitation rate is > 7.6 mm (0.30 in) per hour,[105 or between 10 mm (0.39 in) and 50 mm (2.0 in) per hour');
INSERT INTO rain_type VALUES (4,sysdate,900,50,'Violent Rain','when the precipitation rate is > 50 mm (2.0 in) per hour');

INSERT INTO location VALUES (1,sysdate,'Maria Clara St. to Calamba St.',50.0,7.0,30.0,15.0,'Blumentritt','');
INSERT INTO location VALUES (2,sysdate,'Makiling St. to Retiro St. / Maceda St.',50.0,15.0,30.0,25.0,'Dimasalang Ave.','');
INSERT INTO location VALUES (3,sysdate,'Antipolo St. to Blumentritt',50.0,10.0,37.0,17.0,'España Blvd.','');
INSERT INTO location VALUES (4,sysdate,'Corner Gastambide St.',50.0,15.0,30.0,25.0,'Legarda St.	','');
INSERT INTO location VALUES (5,sysdate,'Guadal Canal St. to Old Sta. Mesa St.',50.0,7.0,30.0,15.0,'V. Mapa St.','');
