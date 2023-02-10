use pizza

CREATE TABLE DroneManufactor
(
	dmaid INT PRIMARY KEY
);

CREATE TABLE DroneModels
(
	dmoid INT PRIMARY KEY,
	dmaid INT REFERENCES DroneManufactor(dmaid),
	name VARCHAR(30),
	bat_life INT,
	max_speed INT
);


CREATE TABLE Drone
(
	did INT PRIMARY KEY,
	dmoid INT REFERENCES DroneModels(dmoid),
	ser_no INT
);

CREATE TABLE PizzaShop
(
	pname VARCHAR(30) UNIQUE,
	addr VARCHAR(30),
	PRIMARY KEY(pname)
);


CREATE TABLE Customer
(
	cname VARCHAR(30) UNIQUE,
	loyalty_score INT,
	PRIMARY KEY(cname)
);

CREATE TABLE Delivery
(
	pname VARCHAR(30) REFERENCES PizzaShop(pname),
	cname VARCHAR(30) REFERENCES Customer(cname),
	did INT REFERENCES Drone(did),
	date_time DATETIME,
	PRIMARY KEY(pname, cname, did)
);

INSERT INTO DroneManufactor VALUES(1), (2), (3)
INSERT INTO DroneModels VALUES (10, 1, 'D1', 10, 10), (11, 1, 'D2', 10, 10), (12, 2, 'D3', 10, 10), (13, 2, 'D4', 10, 10)
INSERT INTO Drone VALUES (20, 10, 10), (21, 11, 13)
INSERT INTO PizzaShop VALUES ('P1', 'A1'), ('P2', 'A2')
INSERT INTO Customer VALUES ('C1', 10), ('C2', 10)
INSERT INTO Delivery VALUES ('P1', 'C1', 20, null), ('P1', 'C1', 21, NULL), ('P1', 'C2', 20, NULL)
GO


CREATE PROCEDURE addDelivery(@cname VARCHAR(30), @pname VARCHAR(30), @did INT, @date_time DATETIME)
AS
BEGIN
		IF EXISTS(SELECT * FROM Customer WHERE cname = @cname)
			IF EXISTS(SELECT * FROM PizzaShop WHERE pname = @pname)
				IF NOT EXISTS(SELECT * FROM Delivery WHERE pname = @pname AND cname = @cname AND did = @did)
					INSERT INTO Delivery VALUES(@pname, @cname, @did, @date_time)
				ELSE
					raiserror('A DELIVERY ALREADY EXISTS!', 12, 1)
			ELSE
				raiserror('This pizza shop does not exist!',12, 1)
		ELSE
			raiserror('This customer does not exist!', 12, 1)
				
END
GO


CREATE OR ALTER VIEW showManufactors
AS
	SELECT DMA.dmaid
	FROM DroneManufactor DMA INNER JOIN DroneModels DMO ON DMA.dmaid = DMO.dmaid
	GROUP BY DMA.dmaid
	HAVING COUNT(*) = (SELECT TOP 1 COUNT(*)
					   FROM DroneManufactor DMA INNER JOIN DroneModels DMO ON DMA.dmaid = DMO.dmaid
					   GROUP BY DMA.dmaid
					   ORDER BY COUNT(*) DESC
					  )
GO

INSERT INTO DroneModels VALUES(100, 1, 'D4', 10, 10)
SELECT * FROM showManufactors
SELECT * FROM DroneModels
go

CREATE OR ALTER FUNCTION customerName(@d INT)
RETURNS TABLE
AS
	RETURN
			SELECT D.cname
			FROM Delivery D
			GROUP BY D.cname 
			HAVING COUNT(*) >= @d
GO

SELECT * FROM customerName(1)