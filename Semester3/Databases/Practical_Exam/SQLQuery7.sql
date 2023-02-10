use flights

CREATE TABLE Airplane
(
	reg_no INT UNIQUE,
	model VARCHAR(30),
	cap INT,
	primary key(reg_no)
);

CREATE TABLE Flight
(
	f_no INT UNIQUE,
	dep_airport VARCHAR(30),
	dest_air VARCHAR(30),
	dep_time DATETIME,
	arr_time DATETIME,
	PRIMARY KEY(f_no),
	reg_no INT REFERENCES Airplane(reg_no)
);

CREATE TABLE Passenger
(
	email_address VARCHAR(30) UNIQUE,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	PRIMARY KEY(email_address)
);

CREATE TABLE Payment
(
	pid INT PRIMARY KEY,
	p_date DATETIME,
	p_type VARCHAR(30)
);

CREATE TABLE Reservation
(
	f_no INT REFERENCES Flight(f_no),
	email_address VARCHAR(30) REFERENCES Passenger(email_address),
	pid INT REFERENCES Payment(pid)
	PRIMARY KEY(f_no, email_address)
);


INSERT INTO Airplane VALUES(1, 'A1', 10)
INSERT INTO Airplane VALUES(2, 'A2', 10)

INSERT INTO Flight VALUES(10, 'Madrid', 'f1', '2020-03-03', '2020-03-04', 1)
INSERT INTO Flight VALUES(11, 'f2', 'f2', '2020-03-03', '2020-03-04', 1)

INSERT INTO Passenger VALUES('p1', 'p1l', 'p1f')
INSERT INTO Passenger VALUES('p2', 'p2l', 'p2f')

INSERT INTO Payment VALUES(20, '2002-10-10', 'p1')
INSERT INTO Payment VALUES(21, '2002-10-10', 'p2')

INSERT INTO Reservation VALUES(10, 'p1', 20)
INSERT INTO Reservation VALUES(11, 'p1', null)
GO

CREATE OR ALTER Procedure addPayment(@f_no INT, @email_address VARCHAR(30), @pid INT)
AS
BEGIN
		IF EXISTS (SELECT * FROM Flight WHERE f_no = @f_no)
			IF EXISTS (SELECT * FROM Passenger WHERE email_address = @email_address)
				IF EXISTS(SELECT * FROM Reservation WHERE @f_no = f_no AND email_address = @email_address)
					IF EXISTS (SELECT * FROM Reservation WHERE @f_no = f_no AND email_address = @email_address AND pid IS NULL)
						UPDATE Reservation SET pid = @pid WHERE f_no = @f_no AND email_address = @email_address
					ELSE
						raiserror ('A PAYMENT FOR THIS RESERVATION ALREADY EXISTS!', 12, 1)
				ELSE
					raiserror ('A RESERVATION DOES NOT EXIST!', 12, 1)
			ELSE
				raiserror('THE PASSENGER DOES NOT EXIST!', 12, 1)
		ELSE
			raiserror('THE FLIGHT DOES NOT EXIST!', 12, 1)

END
GO

exec addPayment 11, 'p1', 21
SELECT * FROM Reservation
GO

CREATE OR ALTER VIEW showMadrid
AS
	SELECT P.first_name, P.last_name
	FROM Passenger P INNER JOIN Reservation R ON P.email_address = R.email_address
	INNER JOIN Flight F ON R.f_no = F.f_no
	WHERE F.dep_airport = 'Madrid'
	GROUP BY P.first_name, P.last_name
	HAVING COUNT(*) > 0
GO

SELECT * FROM showMadrid
GO

CREATE OR ALTER FUNCTION flights(@x INT, @start DATETIME, @end DATETIME)
RETURNS TABLE
AS
	RETURN 
		SELECT F.f_no
		FROM Flight F INNER JOIN Reservation R ON F.f_no = R.f_no
		WHERE R.pid is not NULL AND @start <= F.dep_time AND F.arr_time <= @end
		GROUP BY F.f_no
		HAVING COUNT(*) >= @x
GO

SELECT * FROM flights(1, '2020-03-02', '2020-03-03')

SELECT * FROM Reservation
SELECT * FROM Flight

