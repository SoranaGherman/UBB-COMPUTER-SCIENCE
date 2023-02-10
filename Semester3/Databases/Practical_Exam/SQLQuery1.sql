use bank

-------- a --------

CREATE TABLE Customer
(
	cid INT PRIMARY KEY(cid),
	name VARCHAR(30),
	dob DATE
)

CREATE TABLE BankAcc
(
	bid INT PRIMARY KEY(bid),
	cid INT,
	IBAN INT,
	currBalance INT
	FOREIGN KEY(cid) REFERENCES Customer(cid)
)

CREATE TABLE Cards
(
	cardNumber INT PRIMARY KEY(cardNumber),
	CVV INT,
	bid INT,
	FOREIGN KEY(bid) REFERENCES BankAcc(bid)
)

CREATE TABLE ATM
(
	atmId INT PRIMARY KEY(atmId),
	addr VARCHAR(30)
)

CREATE TABLE Transactions
(
	tid INT PRIMARY KEY(tid),
	cardNumber INT,
	atmId INT,
	transaction_date DATETIME,
	balance INT,
	FOREIGN KEY(cardNumber) REFERENCES Cards(cardNumber),
	FOREIGN KEY(atmID) REFERENCES ATM(atmId)
)

INSERT INTO Customer VALUES(1, 'ana', '01-jan-1970')
INSERT INTO Customer VALUES(2, 'mara', '01-jan-1970')
INSERT INTO Customer VALUES(3, 'sorana', '01-jan-1970')

INSERT INTO BankAcc VALUES(10, 1, 123, 100)
INSERT INTO BankAcc VALUES(11, 2, 254, 200)
INSERT INTO BankAcc VALUES(12, 3, 982, 300)

INSERT INTO Cards VALUES(12345, 345, 10)
INSERT INTO Cards VALUES(12346, 375, 11)
INSERT INTO Cards VALUES(12456, 324, 12)

INSERT INTO ATM VALUES(20, 'ajhbd')
INSERT INTO ATM VALUES(21, 'ffff')
INSERT INTO ATM VALUES(22, 'gggg')

INSERT INTO Transactions VALUES(30, 12345, 20, '10-03-1970', 20)
INSERT INTO Transactions VALUES(31, 12345, 21, '10-03-1970', 20)
INSERT INTO Transactions VALUES(32, 12345, 20, '10-03-1970', 20)
INSERT INTO Transactions VALUES(33, 12346, 20, '10-03-1970', 20)
INSERT INTO Transactions VALUES(34, 12345, 22, '10-03-1970', 20)
GO

------ b --------

CREATE PROCEDURE deleteTransactions (@card INT)
AS
BEGIN
	DELETE FROM Transactions WHERE cardNumber = @card
END
GO

EXEC deleteTransactions 12345

SELECT * FROM Transactions
GO

-------- c ---------

CREATE VIEW showCardNumbers
AS
	SELECT sq.cardNumber
	FROM
		(SELECT DISTINCT T.cardNumber, T.atmId
		 FROM Transactions T
		)as sq
	GROUP BY sq.cardNumber
	HAVING COUNT(*) = (SELECT COUNT(*) FROM ATM)
GO

SELECT * FROM showCardNumbers
GO

CREATE OR ALTER FUNCTION showCards()
RETURNS TABLE
AS
RETURN
		SELECT C.cardNumber, C.CVV
		FROM CARDS C INNER JOIN Transactions T ON C.cardNumber = T.cardNumber
		GROUP BY C.cardNumber, C.CVV
		HAVING SUM (T.balance) > 20
GO


SELECT * FROM showCards()