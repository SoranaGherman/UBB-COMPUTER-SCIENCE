use Swimming_competition
GO

-- TA(aid, a2, ...) table: sid - aid, team_id - a2
CREATE TABLE cadetsSwimmers
(
	sid INT NOT NULL PRIMARY KEY,
	team_id INT UNIQUE NOT NULL,  -- each team has at most one cadet
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL
);

-- TB(bid, b2, ...) table: ncid - bid, age - b2
CREATE TABLE nationalChampionship
(
	ncid INT NOT NULL PRIMARY KEY,
	age INT,
	name VARCHAR(50) NOT NULL
);

-- TC(cid, aid, bid, ...) table: id - cid
CREATE TABLE cadetsSwimmersNationalChampionship
(
	id INT NOT NULL PRIMARY KEY,
	sid INT NOT NULL REFERENCES cadetsSwimmers(sid) ON DELETE CASCADE,
	ncid INT NOT NULL REFERENCES nationalChampionship(ncid) ON DELETE CASCADE,
	host_event VARCHAR(50)
);

EXEC sp_helpindex cadetsSwimmers

--------------------------- a -----------------------------------------------

-- clustered index scan
SELECT * 
FROM cadetsSwimmers
WHERE first_name LIKE '%a'

-- clustered index seek
SELECT * 
FROM cadetsSwimmers
WHERE sid < 6

-- nonclustered index scan
SELECT sid, team_id
FROM cadetsSwimmers

-- nonclustered index seek
SELECT sid, team_id
FROM cadetsSwimmers
WHERE team_id < 15

-- key lookup
SELECT team_id, last_name
FROM cadetsSwimmers
WHERE team_id = 11

---------------------------------------- b --------------------------------------

CREATE TABLE time_table
(
	id INT,
	startTime DATETIME,
	endTime DATETIME
)

DECLARE @start_time DATETIME, @end_time DATETIME

SET @start_time = GETDATE()

SELECT age, name
FROM nationalChampionship
WHERE age = 10
ORDER BY name

SET @end_time = GETDATE()

INSERT INTO time_table VALUES(1, @start_time, @end_time)


CREATE NONCLUSTERED INDEX ageName
ON nationalChampionship(age) INCLUDE (name)

DECLARE @start_time2 DATETIME, @end_time2 DATETIME
SET @start_time2 = GETDATE()

SELECT age, name
FROM nationalChampionship
WHERE age = 10
ORDER BY name

SET @end_time2 = GETDATE()
INSERT INTO time_table VALUES(1, @start_time2, @end_time2)

SELECT * FROM time_table
GO

-------------------------------- c ------------------------------

CREATE VIEW TestView 
AS
SELECT CS.sid, CS.last_name, CSNC.ncid, CSNC.host_event
FROM cadetsSwimmers CS 
INNER JOIN cadetsSwimmersNationalChampionship CSNC ON CS.sid = CSNC.sid
GO

DECLARE @start_date DATETIME, @end_date DATETIME

SET @start_date = GETDATE()
SELECT * FROM TestView
SET @end_date = GETDATE()

INSERT INTO time_table VALUES(2, @start_date, @end_date)

/*
	High cardinality (few duplicate elements in column sid) => it is worth to have a nonclustered index in order not to have to
	scan all scout_ids from SP table in the INNER JOIN
*/


CREATE NONCLUSTERED INDEX NCI1 
ON cadetsSwimmers(sid) INCLUDE(last_name)

CREATE NONCLUSTERED INDEX NCI2
ON cadetsSwimmersNationalChampionship(sid) INCLUDE(ncid, host_event)

DECLARE @start_date2 DATETIME, @end_date2 DATETIME

SET @start_date2 = GETDATE()
SELECT * FROM TestView
SET @end_date2 = GETDATE()

INSERT INTO time_table VALUES(2, @start_date2, @end_date2)

SELECT * FROM cadetsSwimmersNationalChampionship


