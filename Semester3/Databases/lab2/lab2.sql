USE Swimming_competition

/* INSERTS */

INSERT INTO Team 
VALUES(1, 'SPORT CLUB MUNICIPAL PITESTI');

INSERT INTO Team 
VALUES(2, 'SWIM 21 ORADEA');

INSERT INTO Team 
VALUES(3, 'ATENA SPORT CLUB CONSTANTA');

INSERT INTO Team 
VALUES(4, 'EURO SWIMMING CLUB BUCURESTI');

INSERT INTO Team
VALUES(5, 'MILITARI aqua01 BUCURESTI');

INSERT INTO Team
VALUES(7, 'MILITARI steaua BUCURESTI');

/* ........................................................ */

INSERT INTO Category
VALUES(10, 'junior');

INSERT INTO Category
VALUES(11, 'senior');

INSERT INTO Category
VALUES(12, 'cadet');

/* ......................................................... */

INSERT INTO SWIMMER
VALUES(20, 1, 10, 'Pop Andrei', '2005-06-10', 'Prahova');

INSERT INTO SWIMMER
VALUES(21, 3, 12, 'Moldovan Ana', '2010-04-21', 'Constanta');

INSERT INTO SWIMMER
VALUES(22, 4, 11, 'Popescu Robert', '2001-03-20', 'Bucuresti');

INSERT INTO SWIMMER
VALUES(23, 2, 11, 'Valea Maria', '1999-10-10', 'Bihor');

INSERT INTO SWIMMER
VALUES(24, 1, 12, 'Marte Daniel', '2011-07-02', 'Prahova');

INSERT INTO SWIMMER
VALUES(25, 1, 12, 'Marinescu Oana', '2011-09-03', NULL);

INSERT INTO SWIMMER
VALUES(26, 5, 12, 'Gherman Cosmin', '2011-09-03', NULL);

INSERT INTO SWIMMER
VALUES(27, 7, 12, 'Mereu Alin', '2011-09-03', NULL);

INSERT INTO SWIMMER
VALUES(28, 5, 12, NULL, '2002-03-20', 'Bucuresti');

INSERT INTO SWIMMER
VALUES(29, 5, 12, 'Rus Marian', '2001-03-20', 'Bucuresti');


/* ......................................................... */

INSERT INTO Coach
VALUES(30, 1, 'Moldovan Andrei');

INSERT INTO Coach
VALUES(31, 2, 'Mantea Cristina');

INSERT INTO Coach
VALUES(32, 3, 'Florea Lorena');

INSERT INTO Coach
VALUES(33, 4, 'Popescu Rares');

/* ......................................................... */

INSERT INTO Laps
VALUES(40, 50);

INSERT INTO Laps
VALUES(41, 100);

INSERT INTO Laps
VALUES(42, 200);

INSERT INTO Laps
VALUES(43, 400);

INSERT INTO Laps
VALUES(44, 800);

INSERT INTO Laps
VALUES(45, 1500);

INSERT INTO Laps
VALUES(46, 1451);

INSERT INTO Laps
VALUES(47, 111);

/* ......................................................... */

INSERT INTO Styles
VALUES(50, 'Freestyle');

INSERT INTO Styles
VALUES(51, 'Backstroke');

INSERT INTO Styles
VALUES(52, 'Breaststroke');

INSERT INTO Styles
VALUES(53, 'Butterfly');

INSERT INTO Styles
VALUES(54, 'Individual medley');

INSERT INTO Styles
VALUES(55, 'Medley relay');

INSERT INTO Styles
VALUES(56, 'Freestyle relay');

INSERT INTO Styles
VALUES(57, 'random');

/* ......................................................... */

INSERT INTO Swimming_comp
VALUES(60, 40, 50);

INSERT INTO Swimming_comp
VALUES(61, 42, 51);

INSERT INTO Swimming_comp
VALUES(62, 45, 52);

INSERT INTO Swimming_comp
VALUES(63, 41, 53);

/* ......................................................... */

INSERT INTO Register
VALUES(1, 63);

INSERT INTO Register
VALUES(2, 60);

INSERT INTO Register
VALUES(3, 61);

INSERT INTO Register
VALUES(4, 62);

INSERT INTO Register
VALUES(1, 62);

INSERT INTO Register
VALUES(1, 61);

INSERT INTO Register
VALUES(1, 60);

/* ......................................................... */

INSERT INTO Fan
VALUES(70, 'Viorel Alexandra', '2002-11-22');

INSERT INTO Fan
VALUES(71, 'Gherman Sorana', '2002-03-20');

INSERT INTO Fan
VALUES(72, 'Brehar Ruxandra', '2010-10-20');

INSERT INTO Fan
VALUES(73, 'Dragan Daria', '2012-03-20');

/* ......................................................... */

INSERT INTO Fan_of_Swimmer
VALUES(20, 73);

INSERT INTO Fan_of_Swimmer
VALUES(21, 70);

INSERT INTO Fan_of_Swimmer
VALUES(22, 72);

INSERT INTO Fan_of_Swimmer
VALUES(23, 71);

INSERT INTO Fan_of_Swimmer
VALUES(23, 72);

/* Insert violating referential integrity constraint (tid is a foreign key, but the given ID is not valid) */
INSERT INTO Coach
VALUES(34,6);

/* Insert violating referential integrity constraint (lid: a foreign key, but the given ID is not valid) */
INSERT INTO Swimming_comp
VALUES(64, 47, 56);

/* ......................................................... */

/* UPDATES */

UPDATE SWIMMER
SET county = 'Arges'
WHERE tid = 1 AND county is NULL;

UPDATE SWIMMER
SET county = 'Bucuresti'
WHERE tid IN (5,7) AND county is NULL;

UPDATE SWIMMER
SET name = 'Popovici David'
WHERE tid BETWEEN 5 AND 7 AND county LIKE 'Buc%' AND birth_date = '2002-03-20';

UPDATE Laps
SET length = 200
WHERE length % 2 = 1 AND length BETWEEN 100 AND 199;

UPDATE Styles
SET name = 'INVALID'
WHERE name NOT IN ('Freestyle', 'Backstroke', 'Breaststroke', 'Butterfly', 'Individual medley', 'Medley relay', 'Freestyle relay');


/* ......................................................... */

/* DELETES */

DELETE FROM SWIMMER WHERE catid = 12 AND birth_date NOT BETWEEN '2006-01-01' AND '2012-01-01';
DELETE FROM Styles WHERE LEN(name) < 8; 
DELETE FROM Laps WHERE length % 2 != 0 OR length < 50;

/* UNIONS :
			- Select all the teams that have at least 1 swimmer that is a cadet or the team has been registred to at least a competition
			- Select all the swimmers that are juniors or seniors or have at least a fan.

*/

SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid from SWIMMER WHERE catid = 12)
UNION
SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid FROM Register);

SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid from SWIMMER WHERE catid = 12) OR tid = ANY(SELECT tid FROM Register);

SELECT sid, catid, name
FROM SWIMMER
WHERE sid = ANY(SELECT sid FROM Fan_of_Swimmer) 
UNION
SELECT sid, catid, name
FROM SWIMMER
WHERE sid = 10 OR sid = 11;

SELECT sid, catid, name
FROM SWIMMER
WHERE sid = ANY(SELECT sid FROM Fan_of_Swimmer) OR sid = 10 OR sid = 11;


/* INTERSECTIONS :
			- Select all the teams that have at least 1 swimmer that is a cadet and the team has been registred to at least a competition
			- Select all the swimmers that are juniors and have at least a fan.

*/

SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid from SWIMMER WHERE catid = 12)
INTERSECT
SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid FROM Register);

SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid from SWIMMER WHERE catid = 12) AND tid = ANY(SELECT tid FROM Register);

SELECT sid, catid, name
FROM SWIMMER
WHERE sid = ANY(SELECT sid FROM Fan_of_Swimmer)
INTERSECT
SELECT sid, catid, name
FROM SWIMMER
WHERE catid = 11;

SELECT sid, catid, name
FROM SWIMMER
WHERE sid = ANY(SELECT sid FROM Fan_of_Swimmer) AND catid = 11;


/* EXCEPTS :
		- Select all the teams that have at least 1 swimmer that is a cadet BUT the team has NOT been registred to any competitions
		- Select all the swimmers that are cadets BUT do NOT have any fans.
*/


SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid from SWIMMER WHERE catid = 12)
EXCEPT
SELECT tid, name
FROM Team
WHERE tid = ANY(SELECT tid FROM Register);

SELECT sid, name
FROM SWIMMER
WHERE catid = 12 AND sid NOT IN (SELECT sid FROM Fan_of_Swimmer);

/* INNER JOIN:
			- select the fans of the swimmers who are part of teams that have been registered to competitions (two m:n relationships)
*/

SELECT *
FROM Fan F
INNER JOIN Fan_of_Swimmer FS ON F.fid = FS.fid
INNER JOIN SWIMMER S ON FS.sid = S.sid
INNER JOIN TEAM T ON S.tid = T.tid
INNER JOIN Register R ON T.tid = R.tid
INNER JOIN Swimming_comp SC ON R.swc = SC.swc;


/* LEFT JOIN:
			- select the fans of SENIOR swimmers, including the fans of other categories
*/

SELECT *
FROM Fan_of_Swimmer F
LEFT JOIN SWIMMER S ON F.sid = S.sid
LEFT JOIN Category C ON S.catid = C.catid;

/* RIGHT JOIN:
			- select all the fans of swimmers and the swimmers's names, including the swimmers with no fans
*/

SELECT *
FROM Fan_of_Swimmer F
RIGHT JOIN SWIMMER S ON F.sid = S.sid;

/* FULL JOIN:
			- find all the swimmers that are part of a team and have a coach. Include teams with no swimmers but a coach and teams with swimmers but not a coach yet.
*/

SELECT * 
FROM Coach C
FULL JOIN SWIMMER S ON S.tid = C.tid;

/* IN + SUBQUERIES: 
				- Select the swimmers that are in a team that is registered to at least a competition
				- Select the coaches of the teams that have cadet swimmers
*/

SELECT S.name
FROM SWIMMER S
WHERE S.tid IN(
		SELECT T.tid
		FROM Team T WHERE T.tid IN(
							SELECT R.tid
							FROM Register R
							)
		);

SELECT C.name
FROM Coach C 
WHERE C.tid IN(
		SELECT T.tid
		FROM Team T WHERE T.tid IN(
							SELECT S.tid
							FROM SWIMMER S WHERE S.catid = 12
							)
		);

/* EXIST:
		- Select all the teams that have been registered to at least a competition containing the 50 freestyle probe.
		- Select all the swimmers that are cadets and have at least a fan
*/

SELECT DISTINCT T.name
FROM Team T, Register R
WHERE T.tid = R.tid AND EXISTS(
				SELECT SC.swc
				FROM Swimming_comp SC
				WHERE SC.lid = 40 AND SC.stid = 50 AND R.swc = SC.swc
				);


SELECT S.name
FROM SWIMMER S
WHERE S.catid = 11 AND EXISTS(SELECT * FROM Fan_of_Swimmer FS WHERE FS.sid = S.sid);


/* two queries with a subquery in the FROM clause:
												- Select all swimmers that are part of a team prepared by Moldova Andrei
												- Select all fans's names of senior swimmers
*/

SELECT S.name
FROM SWIMMER S, ( SELECT C.tid
				  FROM Coach C
				  WHERE C.name = 'Moldovan Andrei'
				 ) as TC
WHERE S.tid = TC.tid;

SELECT DISTINCT F.name
FROM Fan F, ( SELECT FS.fid
			  FROM Fan_of_Swimmer FS
			  WHERE FS.sid IN (
								SELECT S.sid
								FROM SWIMMER S
								WHERE S.catid = 11
							  )
			) AS FOS
WHERE F.fid = FOS.fid;

/*
	Four queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause
	Use the aggregation operators: COUNT, SUM, AVG, MIN, MAX.
	1. Select the people that are fans of the maximum number of swimmers
	2. Select the TOP 2 people that are fans of the minimum number of swimmers ordered by name
	3. Select the fans of exactly one swimmer
	4. Select TOP 10 swimmers that have fans (ordered by the nr of fans in decreasing order)
*/

SELECT F.name, COUNT(FS1.sid) AS 'Nr. of swimmers'
FROM Fan F
INNER JOIN Fan_of_Swimmer FS1 ON FS1.fid = f.fid
GROUP BY F.name
HAVING COUNT(*) = (SELECT MAX(sww.nr_sw) FROM (SELECT COUNT(*) AS nr_sw
				   FROM Fan_of_Swimmer 
				   GROUP BY fid) sww ); 

SELECT TOP 2 F.name, COUNT(FS1.sid) AS 'Nr. of swimmers'
FROM Fan F
INNER JOIN Fan_of_Swimmer FS1 ON FS1.fid = f.fid
GROUP BY F.name
HAVING COUNT(*) = (SELECT MIN(sww.nr_sw) FROM (SELECT COUNT(*) AS nr_sw
				   FROM Fan_of_Swimmer 
				   GROUP BY fid) sww ) ORDER BY F.name; 

SELECT F.name, COUNT(FS.sid) as 'Nr of fans'
FROM Fan F
INNER JOIN Fan_of_Swimmer FS ON FS.fid = f.fid
GROUP BY F.name
HAVING COUNT(FS.sid) = 1;

SELECT TOP 10 S.name, COUNT(FS.sid) as 'Nr of fans'
FROM SWIMMER S
INNER JOIN Fan_of_Swimmer FS ON FS.sid =  S.sid
GROUP BY S.name
ORDER BY COUNT(FS.sid) DESC;


/*Four queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator).
  Rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.

  1. Select the swimmers that have at least a fan.
  2. Select the teams that have registered to at least a competition.
  3. Find the teams that have NOT been registered to none of the competitions available.
  4. Find the fans that are younger than all swimmers that have fans.

*/

SELECT name
FROM SWIMMER
WHERE sid = ANY(SELECT sid FROM Fan_of_Swimmer);

SELECT name
FROM SWIMMER
WHERE sid IN (SELECT sid FROM Fan_of_Swimmer);

SELECT name
FROM Team
WHERE tid = ANY(SELECT tid FROM Register);

SELECT name
FROM Team
WHERE tid IN (SELECT tid FROM Register);

SELECT tid, name
FROM Team
WHERE tid <> ALL(SELECT tid FROM Register);

SELECT T.tid, T.name
FROM Team T
WHERE (
	SELECT COUNT(*)
	FROM REGISTER R
	WHERE R.tid = T.tid
	) = 0;


SELECT F.name
FROM Fan F 
WHERE F.date_of_birth > ALL(SELECT S.birth_date
							FROM SWIMMER S
							INNER JOIN Fan_of_Swimmer FS ON FS.sid = S.sid);

SELECT F.name
FROM Fan F 
WHERE F.date_of_birth > (SELECT MAX(S.birth_date)
						 FROM SWIMMER S
						 INNER JOIN Fan_of_Swimmer FS ON FS.sid = S.sid);

/*  Select distinct swimmers with their fans */

SELECT DISTINCT S.name, F.name
FROM SWIMMER S, Fan F
WHERE F.fid IN (SELECT fid FROM Fan_of_Swimmer FS WHERE FS.sid = S.sid);

/* How many cadets are needed in order for the first team to register 4 relays in competitions. A relay consists in 4 swimmers.*/

SELECT 16 - COUNT(tid) as 'Nr of cadets to sign in team'
FROM SWIMMER
WHERE tid = 1 AND catid = 12;

/* Count the number of distinct fans for the second team */

SELECT COUNT(DISTINCT FS.sid)
FROM Fan_of_Swimmer FS
WHERE FS.sid IN (SELECT sid FROM SWIMMER WHERE tid = 2);

/* Count the number of swimmers from Bucharest and Prahova */

SELECT COUNT(sid)
FROM SWIMMER
WHERE county IN ('Bucuresti','Prahova');

/* How many styles are included in 50 laps in competitions */

SELECT COUNT(stid) 
FROM Styles
WHERE stid IN (SELECT stid FROM Swimming_comp WHERE lid = 40);