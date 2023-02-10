use Swimming_competition
Go


/*
	A view with a SELECT statement operating on one table
	-> select all swimmers who are cadets
*/

CREATE OR ALTER VIEW swimmers_with_fans
AS
	SELECT S.sid, S.name
	FROM SWIMMER S
	WHERE S.catid = 11
GO

SELECT * FROM swimmers_with_fans
GO

/*
	a view with a SELECT statement that operates on at least 2 different tables and contains at least one JOIN operator
	-> select all teams that have a coach
*/

CREATE OR ALTER VIEW coach_teams
AS
	SELECT C.tid, T.name
	FROM Team T
 	INNER JOIN Coach C ON C.tid = T.tid
GO

SELECT * FROM coach_teams
GO

/*
	A view with a SELECT statement that has a GROUP BY clause, operates on at least 2 different tables and contains at least one JOIN operator.
	-> Select TOP 5 swimmers that have fans (ordered by the nr of fans in decreasing order)
*/

CREATE OR ALTER VIEW top_swimmers_with_fans
AS
	SELECT TOP 5 S.name, COUNT(FS.sid) as 'Nr of fans'
	FROM SWIMMER S
	INNER JOIN Fan_of_Swimmer FS ON FS.sid =  S.sid
	GROUP BY S.name
	ORDER BY COUNT(FS.sid) DESC;
GO

SELECT * FROM top_swimmers_with_fans

CREATE TABLE Nutrition
(
	tid INT NOT NULL,
	coid INT NOT NULL,
	diet VARCHAR(30),
	PRIMARY KEY(tid, coid)
)