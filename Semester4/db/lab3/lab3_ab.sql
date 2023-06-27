use Swimming_competition;
GO

CREATE OR ALTER PROCEDURE addRowLaps (@length INT) AS
    DECLARE @maxId INT
	SET @maxId = 0
	SELECT TOP 1 @maxId = lid + 1 FROM Laps ORDER BY lid DESC
	IF (@length < 0)
	BEGIN
		RAISERROR('Laps length must be grater than zero!', 24, 1);
	END
	IF (@length != 50 AND @length != 100 AND @length != 200 AND @length != 400 AND @length != 800 AND @length != 1500)
	BEGIN
		RAISERROR('This is not an accepted length!', 24, 1);
	END

	INSERT INTO Laps (lid, length) VALUES (@maxId, @length)
    EXEC sp_log_changes null, @length, 'Added row to Laps', null
GO


CREATE OR ALTER PROCEDURE addRowStyles (@name varchar(100)) AS
    DECLARE @maxId INT
	SET @maxId = 0
	SELECT TOP 1 @maxId = stid + 1 FROM Styles ORDER BY stid DESC
	IF (@name is null)
	BEGIN
		RAISERROR('Style name must not be null', 24, 1);
	END
	IF (@name != 'Backstroke' AND @name != 'Breastroke' AND @name != 'Freestyle' AND @name != 'Butterfly')
	BEGIN
		RAISERROR('This name is not a valid style name!', 24, 1);
	END
	
    INSERT INTO Styles (stid, name) values (@maxId, @name)
    exec sp_log_changes null, @name, 'Added row to Styles', null
GO


CREATE OR ALTER PROCEDURE addRowSwimmingCompetition (@LapsLength VARCHAR(50) , @StyleName VARCHAR(50))
AS
	DECLARE @maxId INT
	SET @maxId = 0
	SELECT TOP 1 @maxId = swc + 1 FROM Swimming_comp ORDER BY stid DESC

	IF (@LapsLength < 0)
	BEGIN
		RAISERROR('Laps length must be grater than zero!', 24, 1);
	END

	IF (@LapsLength != 50 AND @LapsLength != 100 AND @LapsLength != 200 AND @LapsLength != 400 AND @LapsLength != 800 AND @LapsLength != 1500)
	BEGIN
		RAISERROR('This is not an accepted length!', 24, 1);
	END

	IF (@StyleName is null)
	BEGIN
		RAISERROR('Style name must not be null', 24, 1);
	END

	IF (@StyleName != 'Backstroke' AND @StyleName != 'Breastroke' AND @StyleName != 'Freestyle' AND @StyleName != 'Butterfly')
	BEGIN
		RAISERROR('This name is not a valid style name!', 24, 1);
	END

	DECLARE @LapsLengthID INT
	SET @LapsLengthID = (SELECT lid FROM Laps WHERE length = @LapsLength)
	DECLARE @StyleNameID INT
	SET @StyleNameID = (SELECT stid FROM Styles WHERE name = @StyleName)
	IF (@LapsLengthID is null)
	BEGIN
		RAISERROR('This Laps length does not exist', 24, 1);
	END
	IF (@StyleNameID is null)
	BEGIN
		RAISERROR('This Style name does not exist', 24, 1);
	END
	INSERT INTO Swimming_comp VALUES (@maxId, @LapsLengthID, @StyleNameID)
	declare @newData varchar(100)
    set @newData = @LapsLength + ' ' + @StyleName
	exec sp_log_changes null, @newData, 'Connected laps length to style name', null
GO


CREATE OR ALTER PROCEDURE rollbackScenarioNoFail
AS
	BEGIN TRAN
	BEGIN TRY
		EXEC addRowLaps 200
		EXEC addRowStyles 'Butterfly'
		EXEC addRowSwimmingCompetition 200, 'Butterfly'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		RETURN
	END CATCH
	COMMIT TRAN
GO


CREATE OR ALTER PROCEDURE rollbackScenarioFail
AS
	BEGIN TRAN
	BEGIN TRY
		EXEC addRowLaps 100
		EXEC addRowStyles 'Freestyle'
		EXEC addRowSwimmingCompetition 400, 'Freestyle'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		RETURN
	END CATCH
	COMMIT TRAN
GO


CREATE OR ALTER PROCEDURE noRollbackScenarioManyToManyNoFail
AS
	DECLARE @ERRORS INT
	SET @ERRORS = 0
	BEGIN TRY
		EXEC addRowLaps 800
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH

	BEGIN TRY
		EXEC addRowStyles 'Breastroke'
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH

	IF (@ERRORS = 0) BEGIN
		BEGIN TRY
			EXEC addRowSwimmingCompetition 800, 'Breastroke'
		END TRY
		BEGIN CATCH
		END CATCH
	END
GO


CREATE OR ALTER PROCEDURE noRollbackScenarioManyToManyFail
AS
	DECLARE @ERRORS INT
	SET @ERRORS = 0
	BEGIN TRY
		EXEC addRowLaps 800
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH

	BEGIN TRY
		EXEC addRowStyles 'Breastroke'
	END TRY
	BEGIN CATCH
		SET @ERRORS = @ERRORS + 1
	END CATCH

	IF (@ERRORS = 0) BEGIN
		BEGIN TRY
			EXEC addRowSwimmingCompetition 800, 'Breastrokeyy'
		END TRY
		BEGIN CATCH
		END CATCH
	END
GO


select * from Laps
select * from Styles
select * from Swimming_comp

DELETE FROM Laps
DELETE FROM Styles
DELETE FROM Swimming_comp

exec rollbackScenarioFail
exec rollbackScenarioNoFail
exec noRollbackScenarioManyToManyFail
exec noRollbackScenarioManyToManyNoFail


