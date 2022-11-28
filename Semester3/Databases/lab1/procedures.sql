USE Swimming_competition
GO

/* MODIFY THE TYPE OF A COLUMN */

CREATE PROCEDURE setSwimmerNameLength
AS
ALTER TABLE SWIMMER
ALTER COLUMN name VARCHAR(30)
EXEC setSwimmerNameLength
GO

CREATE PROCEDURE setSwimmerNameLengthReverse
AS
ALTER TABLE SWIMMER
ALTER COLUMN name VARCHAR(50)
EXEC setSwimmerNameLengthReverse
GO


/* ADD AND REMOVE A COLUMN */

CREATE PROCEDURE addColExperienceCoach
AS
ALTER TABLE Coach
ADD years_of_experience INT
EXEC addColExperienceCoach
GO

CREATE PROCEDURE removeColExperienceCoach
AS
ALTER TABLE Coach
DROP COLUMN years_of_experience
EXEC removeColExperienceCoach
GO


/* ADD AND REMOVE A DEFAULT CONSTRAINT */

CREATE PROCEDURE addDCOnCoachYOExp
AS
ALTER TABLE Coach
ADD CONSTRAINT DEFAULT0 DEFAULT(0) FOR years_of_experience
EXEC addDCOnCoachYOExp
GO

CREATE PROCEDURE removeDCOnCoachYOExp
AS
ALTER TABLE Coach
DROP CONSTRAINT DEFAULT0
EXEC removeDCOnCoachYOExp
GO


/*  CREATE AND DROP A TABLE */

ALTER PROCEDURE addSwimmerCareTaker
AS
CREATE TABLE SwimmerCareTaker (
		ct INT NOT NULL,
		name VARCHAR(30) NOT NULL,
		)
EXEC addSwimmerCareTaker
GO

CREATE PROCEDURE dropSwimmerCareTaker
AS
DROP TABLE SwimmerCareTaker
EXEC dropSwimmerCareTaker
GO


/* ADD AND REMOVE A PRIMARY KEY */

ALTER PROCEDURE addPrimaryKeySwimmerCareTaker
AS
ALTER TABLE SwimmerCareTaker
ADD CONSTRAINT ctIdPK PRIMARY KEY(name) 
EXEC addPrimaryKeySwimmerCareTaker
GO

ALTER PROCEDURE removePrimaryKeySwimmerCareTaker
AS
ALTER TABLE SwimmerCareTaker
DROP CONSTRAINT IF EXISTS ctIdPK
EXEC removePrimaryKeySwimmerCareTaker
GO


/* ADD AND REMOVE A CANDIDATE KEY */

CREATE PROCEDURE addCKCategory
AS
ALTER TABLE Category
ADD CONSTRAINT NCK UNIQUE(name)
EXEC addCKCategory
GO

CREATE PROCEDURE removeCKCategory
AS
ALTER TABLE Category
DROP CONSTRAINT NCK
EXEC removeCKCategory
GO

/* ADD AND REMOVE A FOREIGN KEY */

CREATE PROCEDURE addFKSwimmerCareTaker
AS
ALTER TABLE SwimmerCareTaker
ADD sid INT CONSTRAINT SIFK FOREIGN KEY(sid) REFERENCES SWIMMER(sid)
EXEC addFKSwimmerCareTaker
GO

CREATE PROCEDURE removeFKSwimmerCareTaker
AS
ALTER TABLE SwimmerCareTaker
DROP CONSTRAINT SIFK 
EXEC removeFKSwimmerCareTaker
GO

CREATE TABLE VersionTable(
	version INT
)

INSERT INTO VersionTable VALUES (1)
DELETE FROM VersionTable

CREATE TABLE ProcedureTable(
	fromV INT,
	toV INT,
	PRIMARY KEY(fromV, toV),
	procedureName VARCHAR(100)
)

INSERT INTO ProcedureTable VALUES(1, 2, 'setSwimmerNameLength')
INSERT INTO ProcedureTable VALUES(2, 1, 'setSwimmerNameLengthReverse')

INSERT INTO ProcedureTable VALUES(2, 3, 'addColExperienceCoach')
INSERT INTO ProcedureTable VALUES(3, 2, 'removeColExperienceCoach')

INSERT INTO ProcedureTable VALUES(3, 4, 'addDCOnCoachYOExp')
INSERT INTO ProcedureTable VALUES(4, 3, 'removeDCOnCoachYOExp')

INSERT INTO ProcedureTable VALUES(4, 5, 'addSwimmerCareTaker')
INSERT INTO ProcedureTable VALUES(5, 4, 'removeSwimmerCareTaker')

INSERT INTO ProcedureTable VALUES(5, 6, 'addPrimaryKeySwimmerCareTaker')
INSERT INTO ProcedureTable VALUES(6, 5, 'removePrimaryKeySwimmerCareTaker')

INSERT INTO ProcedureTable VALUES(6, 7, 'addCKCategory')
INSERT INTO ProcedureTable VALUES(7, 6, 'removeCKCategory')

INSERT INTO ProcedureTable VALUES(7, 8, 'addFKSwimmerCareTaker')
INSERT INTO ProcedureTable VALUES(8, 7, 'removeFKSwimmerCareTaker')
GO


CREATE PROCEDURE goToVersion(@newVersion INT) 
AS
	DECLARE @curr INT
	DECLARE @var VARCHAR(100)
	SELECT @curr = version from VersionTable

	IF @newVersion > (SELECT max(toV) FROM ProcedureTable)
		RAISERROR('Bad version', 10, 1)

	WHILE @curr > @newVersion
	BEGIN
		SELECT @var = procedureName FROM ProcedureTable WHERE fromV = @curr AND toV = @curr - 1
		EXEC (@var)
		SET @curr = @curr - 1
	END

	WHILE @curr < @newVersion
	BEGIN
		SELECT @var = procedureName FROM ProcedureTable WHERE fromV = @curr AND toV = @curr + 1
		EXEC (@var)
		SET @curr = @curr + 1
	END

	UPDATE VersionTable SET version = @newVersion

EXEC goToVersion 7
GO


SELECT * FROM VersionTable
