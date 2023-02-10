use trains

CREATE TABLE TrainTypes
(
	ttid INT PRIMARY KEY,
	description VARCHAR(30)
);

CREATE TABLE Train
(
	tid INT PRIMARY KEY,
	name VARCHAR(30),
	ttid INT REFERENCES TrainTypes(ttid)
);

CREATE TABLE Station
(
	sname VARCHAR(30) UNIQUE,
	PRIMARY KEY(sname)
);

CREATE TABLE Route
(
	rname VARCHAR(30) UNIQUE,
	PRIMARY KEY(rname),
	tid INT REFERENCES Train(tid)
);

CREATE TABLE StationsRoutes
(
	sname VARCHAR(30) REFERENCES Station(sname),
	rname VARCHAR(30) REFERENCES Route(rname),
	arrival DATETIME,
	departure DATETIME
	PRIMARY KEY(sname, rname)
);


INSERT INTO TrainTypes VALUES(1, 'TT1')
INSERT INTO TrainTypes VALUES(2, 'TT2')

INSERT INTO Train VALUES(10, 't1', 1)
INSERT INTO Train VALUES(11, 't2', 2)
INSERT INTO Train VALUES(12, 't1', 2)

INSERT INTO Station VALUES('s1')
INSERT INTO Station VALUES('s2')

INSERT INTO Route VALUES('r1', 10)
INSERT INTO Route VALUES('r2', 11)
INSERT INTO Route VALUES('r3', 11)

INSERT INTO StationsRoutes VALUES('s1', 'r1', null, null)
INSERT INTO StationsRoutes VALUES('s2', 'r2', null, null)
INSERT INTO StationsRoutes VALUES('s1', 'r2', null, null)
GO

CREATE OR ALTER PROCEDURE addStationToRoute (@route_name  VARCHAR(30), @station_name VARCHAR(30), @arrival DATETIME, @departure DATETIME)
AS
BEGIN
		IF EXISTS (SELECT * FROM Station WHERE sname = @station_name)
			IF EXISTS (SELECT * FROM Route WHERE rname = @route_name)
				IF NOT EXISTS (SELECT * FROM StationsRoutes WHERE @route_name = rname AND @station_name = sname)
					INSERT INTO StationsRoutes VALUES(@station_name, @route_name, @arrival, @departure)
				ELSE
					BEGIN
					UPDATE StationsRoutes
					SET arrival = @arrival 
					WHERE rname = @route_name AND @station_name = sname

					UPDATE StationsRoutes
					SET departure = @departure 
					WHERE rname = @route_name AND @station_name = sname
					END
			ELSE
				raiserror('Route name does not exist!', 12, 1)
		ELSE
			raiserror('Station name does not exist!', 12, 1)
END

EXEC addStationToRoute 'r4', 's1', '2020-12-01', '2020-12-01'

SELECT * FROM StationsRoutes
GO

CREATE OR ALTER VIEW routesAllStations
AS
	SELECT R.rname 
	FROM Route R INNER JOIN StationsRoutes SR ON R.rname = SR.rname 
	GROUP BY R.rname
	HAVING COUNT(*) = (SELECT COUNT(*) FROM Station)
GO

CREATE OR ALTER FUNCTION stationsWithRRoutes(@r INT)
RETURNS TABLE
AS 
	RETURN 
			SELECT S.sname
			FROM Station S INNER JOIN StationsRoutes SR ON S.sname = SR.sname
			GROUP BY S.sname
			HAVING COUNT(*) >= @r
GO
		
SELECT * FROM stationsWithRRoutes(3)
GO

SELECT * FROM StationsRoutes

