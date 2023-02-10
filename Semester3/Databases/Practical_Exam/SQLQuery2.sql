use shoes

------------ a -----------------------

CREATE TABLE Women
(
	wid INT PRIMARY KEY(wid),
	name VARCHAR(30) NOT NULL,
	max_amount_to_spend INT NOT NULL
);

CREATE TABLE ShoeModel
(
	smid INT PRIMARY KEY(smid),
	name VARCHAR(30),
	season VARCHAR(30),
);

CREATE TABLE Shoes
(
	 shid INT PRIMARY KEY(shid),
	 price INT,
	 smid INT,
	 FOREIGN KEY(smid) REFERENCES ShoeModel(smid) 
);

CREATE TABLE PresentationShow
(
	psid INT PRIMARY KEY(psid),
	name VARCHAR(30),
	city VARCHAR(30)
);

CREATE TABLE PresentationShowShoes
(
	psid INT,
	shid INT,
	nrOfAvailableShoes INT,
	FOREIGN KEY(psid) REFERENCES PresentationShow(psid),
	FOREIGN KEY(shid) REFERENCES Shoes(shid)
);

CREATE TABLE WomenBuyShoes
(
	wid INT,
	shid INT,
	spent_amount INT,
	nr_of_shoes_bought INT,
	FOREIGN KEY(wid) REFERENCES Women(wid),
	FOREIGN KEY(shid) REFERENCES Shoes(shid)
);
GO

INSERT INTO Women VALUES(1, 'ana', 100)
INSERT INTO Women VALUES(2, 'sonia', 10)
INSERT INTO Women VALUES(3, 'marta', 35)
INSERT INTO Women VALUES(4, 'adela', 300)

INSERT INTO ShoeModel VALUES(10, 'adidas', 'autumn')
INSERT INTO ShoeModel VALUES(11, 'nike', 'summer')
INSERT INTO ShoeModel VALUES(12, 'boots', 'winter')
INSERT INTO ShoeModel VALUES(13, 'heels', 'spring')

INSERT INTO Shoes VALUES(20, 10, 10)
INSERT INTO Shoes VALUES(21, 100, 11)
INSERT INTO Shoes VALUES(22, 1000, 13)
INSERT INTO Shoes VALUES(23, 215, 12)

INSERT INTO PresentationShow VALUES(30, 'dhjdd', 'B')
INSERT INTO PresentationShow VALUES(31, 'trrerr', 'BN')
INSERT INTO PresentationShow VALUES(32, 'aaaaaa', 'CJ')
INSERT INTO PresentationShow VALUES(33, 'bbbb', 'VN')

INSERT INTO PresentationShowShoes VALUES(30, 20, 100)
INSERT INTO PresentationShowShoes VALUES(31, 21, 100)
INSERT INTO PresentationShowShoes VALUES(32, 20, 100)
INSERT INTO PresentationShowShoes VALUES(33, 23, 100)
INSERT INTO PresentationShowShoes VALUES(30, 21, 100)
INSERT INTO PresentationShowShoes VALUES(30, 22, 100)
INSERT INTO PresentationShowShoes VALUES(30, 23, 100)
INSERT INTO PresentationShowShoes VALUES(30, 20, 100)
INSERT INTO PresentationShowShoes VALUES(31, 22, 100)

INSERT INTO WomenBuyShoes VALUES(1, 20, 10, 200)
INSERT INTO WomenBuyShoes VALUES(1, 20, 11, 300)
INSERT INTO WomenBuyShoes VALUES(3, 21, 1, 1)
INSERT INTO WomenBuyShoes VALUES(3, 20, 1, 1)
INSERT INTO WomenBuyShoes VALUES(3, 20, 1, 1)
INSERT INTO WomenBuyShoes VALUES(4, 20, 1, 3)
INSERT INTO WomenBuyShoes VALUES(3, 20, 1, 1)

GO


------------ b ---------------

CREATE OR ALTER PROCEDURE addShoeToPresentationShow(@shoe_id INT, @presentationShow_id INT, @nrOfShoes INT)
AS
BEGIN
		INSERT INTO PresentationShowShoes VALUES(@presentationShow_id, @shoe_id, @nrOfShoes)
END
GO

------------ c -------------

CREATE OR ALTER VIEW Women2Shoes
AS
	SELECT DISTINCT W.wid
	FROM Women W INNER JOIN WomenBuyShoes WBS ON W.wid = WBS.wid
	WHERE WBS.shid = 20
	GROUP BY W.wid
	HAVING SUM(WBS.nr_of_shoes_bought) > 2
GO

SELECT * FROM Women2Shoes
GO

--------- d -------------

CREATE OR ALTER FUNCTION ShoesTPresentations(@presentations_count INT)
RETURNS TABLE
AS
RETURN
		SELECT DISTINCT S.shid
		FROM Shoes S INNER JOIN PresentationShowShoes PSS ON S.shid = PSS.shid
		GROUP BY S.shid
		HAVING COUNT(PSS.psid) >= @presentations_count
GO

SELECT * FROM PresentationShowShoes

SELECT * FROM ShoesTPresentations(3)

