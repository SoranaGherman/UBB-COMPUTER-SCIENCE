use test


CREATE TABLE Cousine
(
	cname VARCHAR(30) UNIQUE,
	PRIMARY KEy(cname)
);

CREATE TABLE Vendor
(
	vname VARCHAR(30) UNIQUE,
	cname VARCHAR(30) REFERENCES Cousine(cname)
	PRIMARY KEY(vname)
);

CREATE TABLE Employee
(
	ename VARCHAR(30) UNIQUE,
	vname VARCHAR(30) REFERENCES Vendor(vname),
	dob DATE,
	flag  INT,
	PRIMARY KEY(ename)
);

CREATE TABLE Locations
(
	laddr VARCHAR(30) PRIMARY KEY,
	city VARCHAR(30),

);

CREATE TABLE VendorLocation
(
	vname VARCHAR(30) REFERENCES Vendor(vname),
	laddr VARCHAR(30) REFERENCES Locations(laddr),
	start_date DATETIME,
	end_date DATETIME,
	PRIMARY KEY(vname, laddr)
);

CREATE TABLE Orders
(
	laddr VARCHAR(30) REFERENCES Locations(laddr),
	vname VARCHAR(30) REFERENCES Vendor(vname),
	no_ordered_dishes INT,
	total INT,
	PRIMARY KEY(vname, laddr)
);
GO

DROP TABLE Cousine
DROP TABLE Vendor
DROP TABLE Employee
DROP TABLE Locations
DROP TABLE VendorLocation
DROP TABLE Orders

INSERT INTO COUSINE VALUES('C1'), ('C2')
INSERT INTO VENDOR VALUES ('V1', 'C1'), ('V2', 'C2')
INSERT INTO EMPLOYEE VALUES('E1', 'V1', NULL, 0)
INSERT INTO LOCATIONS VALUES('L1', 'A1'), ('L2', 'A2')
INSERT INTO VENDORLOCATION VALUES('V1', 'L1', 10, 200), ('V2', 'L1', 10, 100), ('V1', 'L2', 10, 10)
INSERT INTO ORDERS VALUES('L1', 'V1', 10, 200), ('L2', 'V2',10, 100)
insert into orders values('L2', 'V1',10, 100)
GO


CREATE OR ALTER PROCEDURE addToVendorLocation(@vname VARCHAR(30), @laddr VARCHAR(30), @start_date DATETIME, @end_date DATETIME)
AS
BEGIN
		IF EXISTS(SELECT * FROM  Vendor WHERE vname = @vname)
			IF EXISTS(SELECT * FROM Locations WHERE laddr = @laddr)
				IF NOT EXISTS(SELECT * FROM VendorLocation WHERE vname = @vname AND laddr = @laddr)
					INSERT INTO VendorLocation VALUES (@vname, @laddr, @start_date, @end_date)
				ELSE
					UPDATE VendorLocation
					SET start_date = @start_date, end_date = @end_date
					WHERE vname = @vname AND laddr = @laddr
			ELSE
				raiserror('Location does not exist', 12, 1)
	  ELSE
		 raiserror('Vendor does not exist', 12, 1)
END
GO

CREATE OR ALTER VIEW showLocations
AS
	SELECT L.laddr
	FROM Locations L INNER JOIN VendorLocation VL ON L.laddr = VL.laddr
	GROUP BY L.laddr
	HAVING COUNT(*) = (SELECT COUNT(*) FROM Vendor)
GO

SELECT * FROM showLocations
GO


CREATE OR ALTER FUNCTION showCount(@R INT, @vv INT)
RETURNS TABLE
AS
	RETURN 
			SELECT RR.vname
			FROM (
					SELECT V.vname, o.TOTAL
					FROM Vendor V INNER JOIN Orders O ON V.vname = O.vname
					GROUP BY V.vname, O.total
					HAVING O.total > @vv
				 ) AS RR
			GROUP BY RR.vname
			HAVING COUNT(*) >= @R
GO

SELECT * FROM ORDERS
GO

CREATE OR ALTER FUNCTION showCount1(@R INT, @vv INT)
RETURNS TABLE
AS
	RETURN 
			SELECT COUNT(*) AS MYRES
			FROM
				(SELECT RR.vname
				FROM (
						SELECT V.vname, o.TOTAL
						FROM Vendor V INNER JOIN Orders O ON V.vname = O.vname
						GROUP BY V.vname, O.total
						HAVING O.total > @vv
					 ) AS RR
				GROUP BY RR.vname
				HAVING COUNT(*) >= @R) AS P
GO

SELECT * FROM ORDERS

SELECT * FROM showCount1(1, 100)



