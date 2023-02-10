use cinemaProductions

CREATE TABLE Company
(
	cid INT PRIMARY KEY(cid),
	name VARCHAR(30)
);

CREATE TABLE StageDirector
(
	sid INT PRIMARY KEY(sid),
	name VARCHAR(30),
	noOfAwards INT
);

CREATE TABLE Movie
(
	 mid INT PRIMARY KEY(mid),
	 name VARCHAR(30),
	 release_date DATE,
	 cid INT,
	 sid INT,
	 FOREIGN KEY(cid) REFERENCES Company(cid),
	 FOREIGN KEY(sid) REFERENCES StageDIrector(sid)
);

CREATE TABLE CinemaProduction
(
	cpid INT PRIMARY KEY(cpid),
	title VARCHAR(30),
	mid INT,
	FOREIGN KEY(mid) REFERENCES Movie(mid)
);

CREATE TABLE Actor
(
	aid INT PRIMARY KEY(aid),
	name VARCHAR(30),
	ranking INT,
);

CREATE TABLE CinemaProductionActor
(
	cpid INT,
	aid INT,
	entryMoment VARCHAR(30),
	FOREIGN KEY(cpid) REFERENCES CinemaProduction(cpid),
	FOREIGN KEY(aid) REFERENCES Actor(aid)
);


INSERT INTO Actor VALUES(1, 'sorana', 80)
INSERT INTO Actor VALUES(2, 'adela', 100)
INSERT INTO Actor VALUES(3, 'cosmin', 40)

INSERT INTO Company VALUES(10, 'leul')
INSERT INTO Company VALUES(11, 'cartea')

INSERT INTO StageDirector VALUES(20, 'norbert', 10)
INSERT INTO StageDirector VALUES(21, 'albert', 3)

INSERT INTO Movie VALUES(30, 'mara', '2018-02-03', 10, 20)
INSERT INTO Movie VALUES(31, 'woman', '2020-10-20', 11, 21)
INSERT INTO Movie VALUES(32, 'wfnfkf', '2020-10-20', 11, 21)

INSERT INTO CinemaProduction VALUES(40, 'aaa', 30)
INSERT INTO CinemaProduction VALUES(41, 'nnnn', 31)
INSERT INTO CinemaProduction VALUES(44, 'nnnn', 32)

GO

CREATE OR ALTER PROCEDURE addActorToCinemaProduction(@actor_id INT, @entry_moment VARCHAR(30), @cinema_production_id INT)
AS
BEGIN
		INSERT INTO CinemaProductionActor VALUES(@cinema_production_id, @actor_id, @entry_moment)
END

EXEC addActorToCinemaProduction 3, 'HKKN', 41
GO

CREATE OR ALTER VIEW actorsInAllCinemaProd
AS
	SELECT A.name
	FROM Actor A INNER JOIN CinemaProductionActor CPA ON A.aid = CPA.aid
	GROUP BY A.name
	HAVING  COUNT(*) = (SELECT COUNT(*) FROM CinemaProduction)
GO

SELECT * FROM actorsInAllCinemaProd
GO

CREATE OR ALTER FUNCTION moviesCertainDateAndProductions(@P INT)
RETURNS TABLE
AS
	RETURN 
			SELECT M.name
			FROM Movie M INNER JOIN CinemaProduction CP ON M.mid = CP.mid
			WHERE M.release_date > '2018-02-03' 
			GROUP BY M.name
			HAVING COUNT(*) >= @P

GO

