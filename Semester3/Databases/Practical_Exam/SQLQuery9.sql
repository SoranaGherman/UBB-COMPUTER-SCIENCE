use restaurants

CREATE TABLE Type
(
	type_id INT PRIMARY KEY,
	name VARCHAR(30),
	description VARCHAR(30)
);

CREATE TABLE Product
(
	prod_id INT PRIMARY KEY,
	name VARCHAR(30),
	description VARCHAR(30),
	type_id INT REFERENCES Type(type_id)
);

CREATE TABLE Restaurant
(
	rname VARCHAR(30) UNIQUE,
	PRIMARY KEY(rname)
);

CREATE TABLE Menu
(
	menu_id INT PRIMARY KEY,
	name VARCHAR(30) UNIQUE,
	rname VARCHAR(30) REFERENCES Restaurant(rname)
);

CREATE TABLE Items
(
	menu_id INT REFERENCES Menu(menu_id),
	prod_id INT REFERENCES Product(prod_id),
	weight INT,
	price INT
	PRIMARY KEY(menu_id, prod_id)
);


INSERT INTO Type VALUES (1, 'T1', 'D1'), (2, 'T2', 'D2')
INSERT INTO Product VALUES(10, 'P1', 'D1', 1), (11, 'P2', 'D2', 2)
INSERT INTO Restaurant VALUES('R1'), ('R2')
INSERT INTO Menu VALUES(20, 'M1', 'R1'), (21, 'M2', 'R2')
INSERT INTO Items VALUES(20, 10, 1, 1), (20, 11, 1, 1), (21, 10, 1, 1)
insert into Menu VALUES(22, 'M3', 'R2')
INSERT INTO Items VALUES(22, 11, 1, 1)
INSERT INTO Product VALUES(12, 'P3', 'D3', 1)
INSERT INTO Items VALUES(22, 12, 1, 1)
INSERT INTO Items VALUES(21, 11, 1, 1)
GO


CREATE OR ALTER PROCEDURE addProductToMenu(@menu_id INT, @prod_id INT, @weight INT, @price INT)
AS
BEGIN
		IF EXISTS (SELECT * FROM Menu WHERE menu_id = @menu_id)
			IF EXISTS (SELECT * FROM Product WHERE prod_id = @prod_id)
				IF NOT EXISTS(SELECT * FROM Items WHERE menu_id = @menu_id AND prod_id = @prod_id)
					INSERT INTO Items VALUES(@menu_id, @prod_id, @weight, @price)
				ELSE
					UPDATE Items 
					SET weight = @weight, price = @price
					WHERE menu_id = @menu_id AND prod_id = @prod_id
			ELSE
				raiserror('The given product does not exist!', 12, 1)
		ELSE
			raiserror('The gove menu does not exist!', 12, 1)
END
GO


CREATE OR ALTER VIEW restaurantsAllProducts
AS
	SELECT R.rname
	FROM
		(SELECT rname, prod_id
		FROM Items INNER JOIN Menu ON Items.menu_id = Menu.menu_id
		GROUP BY rname, prod_id) as R
	GROUP BY R.rname
	HAVING count(*) = (SELECT count(*) FROM Product)

GO



CREATE OR ALTER FUNCTION prodName(@r INT)
RETURNS TABLE
AS
	RETURN
			SELECT P.name
			FROM Product P INNER JOIN Items I ON P.prod_id = I.prod_id
			GROUP BY P.name
			HAVING AVG(I.price) > @r
GO


SELECT * FROM prodName(0)