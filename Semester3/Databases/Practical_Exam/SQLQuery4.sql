use confectionaryStore

CREATE TABLE Chef
(
	chef_id INT PRIMARY KEY,
	name VARCHAR(30),
	gender VARCHAR(30),
	dob DATE
);

CREATE TABLE CakeType
(
	type_id INT PRIMARY KEY,
	name VARCHAR(30),
	description VARCHAR(30)
);

CREATE TABLE Cake
(
	cake_name VARCHAR(30) PRIMARY KEY,
	shape VARCHAR(30),
	weight VARCHAR(30),
	price INT,
	type_id INT,
	FOREIGN KEY(type_id) REFERENCES CakeType(type_id),
);

CREATE TABLE ChefsCakes
(
	cake_name VARCHAR(30) NOT NULL,
	chef_id INT NOT NULL,
	FOREIGN KEY (cake_name) REFERENCES Cake(cake_name),
	FOREIGN KEY(chef_id) REFERENCES Chef(chef_id),
	PRIMARY KEY(cake_name, chef_id)
);

CREATE TABLE Orders
(
	order_id INT PRIMARY KEY,
	order_date DATE
);

CREATE Table CakeOrder
(
	cake_name VARCHAR(30) NOT NULL,
	order_id INT NOT NULL,
	nrOfOrderedPieces INT,
	FOREIGN KEY(cake_name) REFERENCES Cake(cake_name),
	FOREIGN KEY(order_id) REFERENCES Orders(order_id),
	PRIMARY KEY(cake_name, order_id)
);

GO


INSERT INTO Chef VALUES(1, 'sorana', 'female', '2002-03-20')
INSERT INTO Chef VALUES(2, 'norbert', 'male', '2002-03-20')

INSERT INTO CakeType VALUES(10, 'sweet', 'dhjkd')

INSERT INTO Cake VALUES('cremes', 'square', '2.5', 20, 10)
INSERT INTO Cake VALUES('dobos', 'square', '2.5', 20, 10)
INSERT INTO Cake VALUES('rapunzel', 'square', '2.5', 20, 10)

INSERT INTO Orders VALUES(20, '2022-10-20')
INSERT INTO Orders VALUES(21, '2022-10-21')

INSERT INTO ChefsCakes VALUES('cremes', 2)
INSERT INTO ChefsCakes VALUES('rapunzel', 2)


GO

CREATE OR ALTER PROCEDURE addCakeToCakeOrder(@order_id INT, @cake_name VARCHAR(30), @p INT)
AS
BEGIN
		IF (SELECT COUNT(*) FROM CakeOrder CO WHERE CO.cake_name = @cake_name AND CO.order_id = @order_id) = 0
			INSERT INTO CakeOrder VALUES(@cake_name, @order_id, @p)
		ELSE
			UPDATE CakeOrder 
			SET nrOfOrderedPieces = @p
			WHERE cake_name = @cake_name AND order_id = @order_id 
END
GO

EXEC addCakeToCakeOrder 20, 'dobos',  30

SELECT * FROM CakeOrder

GO

CREATE OR ALTER FUNCTION chefsOFAllCakes()
RETURNS TABLE
AS
	RETURN 
			SELECT C.name
			FROM Chef C INNER JOIN ChefsCakes CC ON C.chef_id = CC.chef_id
			GROUP BY C.name
			HAVING COUNT(*) = (SELECT COUNT(*) FROM Cake)

GO

INSERT INTO ChefsCakes VALUES('dobos', 2)

SELECT * FROM chefsOFAllCakes()