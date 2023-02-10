use zoos

CREATE TABLE ZOO
(
	zid INT PRIMARY KEY,
	zoo_admin VARCHAR(30),
	zoo_name VARCHAR(30)
);

CREATE TABLE ANIMAL
(
	aid INT PRIMARY KEY,
	animal_name VARCHAR(30),
	dob DATE,
	zid INT,
	FOREIGN KEY(zid) REFERENCES ZOO(zid)
);

CREATE TABLE FOOD
(
	fid INT PRIMARY KEY,
	food_name VARCHAR(30)
);

CREATE TABLE FOOD_ANIMALS
(
	aid INT,
	fid INT,
	quota INT,
	FOREIGN KEY(aid) REFERENCES ANIMAL(aid),
	FOREIGN KEY(fid) REFERENCES FOOD(fid),
	PRIMARY KEY(aid, fid)
);

CREATE TABLE VISITOR
(
	visitor_id INT PRIMARY KEY,
	visitor_name VARCHAR(30),
	age INT
);

CREATE TABLE VISIT
(
	visit_id INT PRIMARY KEY,
	visit_day VARCHAR(30),
	price INT,
	visitor_id INT,
	zid INT,
	FOREIGN KEY(visitor_id) REFERENCES VISITOR(visitor_id),
	FOREIGN KEY(zid) REFERENCES ZOO(zid)
);

INSERT INTO ZOO VALUES(1, 'AAA', 'BBB')
INSERT INTO ZOO VALUES(2, 'CCC', 'DDD')

INSERT INTO ANIMAL VALUES(10, 'hsk', '2002-10-20', 1)
INSERT INTO ANIMAL VALUES(11, 'tgttt', '2002-10-20', 2)
INSERT INTO ANIMAL VALUES(12, 'tgttt', '2002-10-20', 2)

INSERT INTO FOOD VALUES(20, 'dhjdk')
INSERT INTO FOOD VALUES(21, 'dffggf')

INSERT INTO FOOD_ANIMALS VALUES(10, 20, 10)
INSERT INTO FOOD_ANIMALS VALUES(10, 21, 10)
INSERT INTO FOOD_ANIMALS VALUES(11, 20, 10)

INSERT INTO VISITOR VALUES(30, 'DHJDKD', 30)
INSERT INTO VISITOR VALUES(31, 'DGGFG', 30)

INSERT INTO VISIT VALUES(40, 'DNLKF', 30, 30, 1)
INSERT INTO VISIT VALUES(41, 'FGGFFGFG', 30, 30, 2)
INSERT INTO VISIT VALUES(42, 'FGGFFGFG', 30, 30, 2)
GO

CREATE OR ALTER PROCEDURE deleteDataAboutAnimal(@animal_id INT)
AS
BEGIN
		IF (SELECT COUNT(aid) FROM ANIMAL WHERE aid = @animal_id) <= 0 
			print('There is no animal with this id')
		ELSE
			DELETE FROM FOOD_ANIMALS WHERE aid = @animal_id
END
GO

exec deleteDataAboutAnimal 10
GO


CREATE OR ALTER VIEW selectMinZoos
AS
	SELECT Z.zid
	FROM ZOO Z LEFT JOIN VISIT V ON Z.zid = V.zid
	GROUP BY Z.zid
	HAVING COUNT(visit_id) = (SELECT TOP 1 COUNT(V.visit_id) AS NoVisits 
							  FROM ZOO LEFT JOIN VISIT ON ZOO.zid = VISIT.zid 
							  GROUP BY ZOO.zid 
							  ORDER BY NoVisits
							 )
GO


CREATE OR ALTER FUNCTION visitors(@no_of_animals INT)
RETURNS TABLE
AS
	RETURN
			SELECT V.visitor_id
			FROM VISITOR V INNER JOIN VISIT VV ON V.visitor_id = VV.visitor_id
			WHERE (SELECT COUNT(*) FROM ANIMAL A WHERE A.zid = VV.zid) > @no_of_animals
			GROUP BY V.visitor_id
			
GO


SELECT * FROM selectMinZoos