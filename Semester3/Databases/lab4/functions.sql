use Swimming_competition
GO

/*
	-------------------- Random Generators -------------------- 
*/

CREATE PROCEDURE generate_random_string(@string VARCHAR(16) OUTPUT)
AS
BEGIN
	DECLARE @counter INT = 0;
	DECLARE @limit INT = 5 + RAND() * 15;

	SET @string = '';
	WHILE (@counter < @limit)
	BEGIN
		SET @string = @string + CHAR((RAND()*25 + 97));
		SET @counter = @counter + 1;
	END
END
GO

CREATE PROCEDURE generate_random_integer(@low_limit INT, @max_limit INT, @integer INT OUTPUT)
AS
BEGIN
	SET @integer = @low_limit + RAND() * @max_limit;
END
GO

/*
	-------------------- PRIMARY KEY RETRIVAL -------------------- 
*/

CREATE OR ALTER FUNCTION IsPrimaryKey (@table VARCHAR(128), @column VARCHAR(128))
RETURNS INT
AS
BEGIN
	DECLARE @counter INT = 0
	SET @counter = (
		SELECT count(*)
		FROM     INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS C
			JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE  AS K ON C.TABLE_NAME = K.TABLE_NAME
																 AND C.CONSTRAINT_CATALOG = K.CONSTRAINT_CATALOG
																 AND C.CONSTRAINT_SCHEMA = K.CONSTRAINT_SCHEMA
																 AND C.CONSTRAINT_NAME = K.CONSTRAINT_NAME
		WHERE   C.CONSTRAINT_TYPE = 'PRIMARY KEY'
				AND K.COLUMN_NAME = @column
				AND C.TABLE_NAME = @table
		)

	IF @counter = 0 BEGIN
		RETURN 0
	END

	RETURN 1
END
GO

/*
	-------------------- FOREIGN KEY RETRIVAL -------------------- 
*/

CREATE OR ALTER PROCEDURE GetReferenceData (@table VARCHAR(128), @column VARCHAR(128), @referencedTable VARCHAR(128) OUTPUT, @referencedColumn VARCHAR(128) OUTPUT)
AS
BEGIN
	 SELECT @referencedTable = OBJECT_NAME(FC.referenced_object_id), @referencedColumn = COL_NAME(FC.referenced_object_id, FC.referenced_column_id)
	 FROM sys.foreign_keys AS F INNER JOIN sys.foreign_key_columns AS FC ON F.OBJECT_ID = FC.constraint_object_id
	 WHERE OBJECT_NAME (F.parent_object_id) = @table AND COL_NAME(FC.parent_object_id, FC.parent_column_id) = @column

END
GO

/*
	----------------------------- Row Insertion -----------------------------
*/

-- Inserts a single row of random data in a given table
-- Data types: varchar or int.

CREATE OR ALTER PROCEDURE row_insert (@table_name varchar(50))
AS
BEGIN
	DECLARE @get_columns_query NVARCHAR(MAX) = N'
		SELECT COLUMN_NAME, DATA_TYPE
		FROM INFORMATION_SCHEMA.COLUMNS
		WHERE TABLE_NAME = ''' + @table_name + '''
	';
	DECLARE @insert_query NVARCHAR(MAX) = N'INSERT INTO ' + @table_name;

	DECLARE @columns NVARCHAR(MAX);
	DECLARE @types NVARCHAR(MAX);
	DECLARE @rows_number INT = 0;

	DECLARE @cursor_q NVARCHAR(MAX) = N'
			DECLARE @column_name NVARCHAR(MAX);
			DECLARE @data_type NVARCHAR(MAX);

			DECLARE column_cursor CURSOR FOR ' + @get_columns_query + '
			OPEN column_cursor
			FETCH column_cursor
			INTO @column_name, @data_type;

			IF @@FETCH_STATUS = 0
			BEGIN
				SET @columns = @column_name;
				SET @types = @data_type;
				SET @rows_number = 1;
			END

			WHILE @@FETCH_STATUS = 0
			BEGIN
				FETCH column_cursor
				INTO @column_name, @data_type;
				IF @@FETCH_STATUS = 0
				BEGIN
					SET @columns = @columns + '', '' + @column_name;
					SET @types = @types + '', '' + @data_type;
					SET @rows_number = @rows_number + 1;
				END
			END
			CLOSE column_cursor;
			DEALLOCATE column_cursor;
	';

	EXEC sp_executesql @Query = @cursor_q, @Params = N'@columns NVARCHAR(MAX) OUTPUT, @types NVARCHAR(MAX) OUTPUT, @rows_number INT OUTPUT', 
	@columns = @columns OUTPUT, @types = @types OUTPUT, @rows_number = @rows_number OUTPUT;

	SET @insert_query = @insert_query + '(' + @columns + ') VALUES (';

	SET @types = @types + ', ';
	SET @columns = @columns + ', ';

	DECLARE @index INT = 0;
	DECLARE @current NVARCHAR(MAX);
	DECLARE @current_column NVARCHAR(MAX);

	WHILE @index < @rows_number
	BEGIN
		SET @current = LEFT(@types, CHARINDEX(', ', @types) - 1);
		SET @types = SUBSTRING(@types, CHARINDEX(', ', @types) + 2, LEN(@types));

		SET @current_column = LEFT(@columns, CHARINDEX(', ', @columns) - 1);
		SET @columns = SUBSTRING(@columns, CHARINDEX(', ', @columns) + 2, LEN(@columns));

		IF @index != 0
			SET @insert_query = @insert_query + ', ';

		DECLARE @referenced_table NVARCHAR(MAX) = '';
		DECLARE @referenced_column NVARCHAR(MAX) = '';

		EXEC GetReferenceData @table_name, @current_column, @referenced_table OUTPUT, @referenced_column OUTPUT

		IF @current = 'int'
		BEGIN
			 IF @referenced_table = '' AND @referenced_column = ''
			 BEGIN
				IF (SELECT dbo.IsPrimaryKey(@table_name, @current_column)) = 1
				BEGIN
					DECLARE @integerPK INT;

					DECLARE @QUERY1 NVARCHAR(MAX)
					SET @QUERY1 = N'SELECT @integerPK = MAX (' + CONVERT(NVARCHAR(MAX), @current_column) + N') FROM ' + CONVERT(NVARCHAR(MAX), @table_name)
					EXEC sp_executesql @QUERY1, N' @integerPK INT OUTPUT', @integerPK OUTPUT
		
					IF @integerPK IS NULL
					BEGIN
						SET @integerPK = 1
					END
					ELSE
					BEGIN
						SET @integerPK = @integerPK + 1
					END

					SET @insert_query = @insert_query + CONVERT(NVARCHAR(MAX), @integerPK);
				END

				ELSE

				BEGIN
					DECLARE @integer INT;
					EXEC generate_random_integer 0, 100000000, @integer = @integer OUTPUT;
					SET @insert_query = @insert_query + CONVERT(NVARCHAR(MAX), @integer);
				END
			END
			ELSE
			BEGIN
				DECLARE @int_value INT;

				DECLARE @int_query NVARCHAR(MAX) = N'SELECT @int_value = ' + @referenced_column + ' FROM ' + @referenced_table +
						' WHERE ' + @referenced_column + ' NOT IN (SELECT ' + @current_column + ' FROM ' + @table_name + ')';

				EXEC sp_executesql @Query = @int_query, @Params = N'@int_value INT OUTPUT', @int_value = @int_value OUTPUT;
				SET @insert_query = @insert_query + CONVERT(NVARCHAR(MAX), @int_value);
			END
		END

		IF @current = 'varchar'
		BEGIN
				IF @referenced_table = '' AND @referenced_column = ''
				BEGIN
				DECLARE @string NVARCHAR(16);
				EXEC generate_random_string @string = @string OUTPUT;
				SET @insert_query = @insert_query + '''' + @string + '''';
				END
				ELSE
				BEGIN
					DECLARE @str_value NVARCHAR(MAX);

					DECLARE @str_query NVARCHAR(MAX) = N'SELECT @str_value = ' + @referenced_column + ' FROM ' + @referenced_table;

					EXEC sp_executesql @Query = @str_query, @Params = N'@str_value NVARCHAR(MAX) OUTPUT', @str_value = @str_value OUTPUT;
					SET @insert_query = @insert_query + '''' + @str_value + '''';
				END
		END

		SET @index = @index + 1;
	END

	SET @insert_query = @insert_query + ');';
	EXEC (@insert_query)
END
GO


/*
-------------- MULTIPLE ROWS INSERTION -----------------
insert a given number of rows of random data in a given table
*/

CREATE OR ALTER PROCEDURE multiple_rows_insertion(@table_name NVARCHAR(MAX), @rows_count INT)
AS
BEGIN
		WHILE @rows_count > 0
		BEGIN
				EXEC row_insert @table_name
				SET @rows_count = @rows_count - 1
		END
END
GO


/*
------------------- ADD TEST, ADD TABLE, TestTables ------------------------
*/

CREATE OR ALTER PROCEDURE add_test (@test_name NVARCHAR(MAX))
AS
BEGIN
		INSERT INTO dbo.Tests (Name)
		VALUES (@test_name)
END
GO


CREATE OR ALTER PROCEDURE add_table(@table_name NVARCHAR(MAX))
AS
BEGIN
		INSERT INTO dbo.Tables (Name)
		VALUES (@table_name)
END
GO

EXEC add_table 'Styles'    /* a table with a single-column primary key and no foreign keys */
EXEC add_table 'Coach'	   /* a table with a single-column primary key and at least one foreign key */
EXEC add_table 'Nutrition' /* a table with a multicolumn primary key */
GO


CREATE OR ALTER PROCEDURE add_test_table (@test_id INT, @table_id INT, @nr_rows INT, @pos INT)
AS
BEGIN
		INSERT INTO dbo.TestTables (TestID, TableID, NoOfRows, Position)
		VALUES (@test_id, @table_id, @nr_rows, @pos)

END
GO


/*
--------------- ADD VIEW, ADD TEST VIEW -------------------
*/

CREATE OR ALTER PROCEDURE add_view(@table_name NVARCHAR(MAX))
AS
BEGIN
		INSERT INTO dbo.Views(Name)
		VALUES (@table_name)
END
GO


CREATE OR ALTER PROCEDURE add_test_view (@test_id INT, @view_id INT)
AS
BEGIN
		INSERT INTO dbo.TestViews 
		VALUES (@test_id, @view_id)
END
GO


/*
--------------- RUN TEST ----------------
*/

CREATE OR ALTER PROCEDURE run_test(@test_id INT)
AS
BEGIN
		DECLARE @start_test DATETIME = GETDATE()
		DECLARE @table_id INT = -1
		DECLARE @view_id INT = -1
		DECLARE @no_of_rows INT = -1
		DECLARE @run_id INT = -1

		INSERT INTO dbo.TestRuns (Description, StartAt)
		VALUES ((SELECT Name FROM dbo.Tests WHERE TestID = @test_id), @start_test)

		SELECT @run_id = TestRunID FROM TestRuns
		WHERE Description = (SELECT Name FROM Tests WHERE TestID = @test_id) AND StartAt = @start_test

		DECLARE @table_name NVARCHAR(MAX) = ''
		DECLARE @query NVARCHAR(MAX) = ''

		/*--------- DELETE FROM TABLES OF TEST TABLE ------- */

		DECLARE cursor_test CURSOR FOR 
		SELECT TableID FROM TestTables
		WHERE TestID = CONVERT(INT, @test_id)
		ORDER BY Position

		OPEN cursor_test
		FETCH cursor_test
		INTO @table_id

		WHILE @@FETCH_STATUS = 0
		BEGIN
			SELECT @table_name = Name FROM Tables
			WHERE TableID = CONVERT(INT, @table_id)

			SET @query = N'DELETE FROM ' + @table_name
			EXEC sp_executesql @query

			FETCH cursor_test
			INTO @table_id
		END

		CLOSE cursor_test
		DEALLOCATE cursor_test

		/* ---------- INSERT INTO TABLES FROM TEST TABLE ---------- */

		DECLARE TestCursor CURSOR FOR 
		SELECT TableID, NoOfRows FROM TestTables
		WHERE TestID = CONVERT(INT, @test_id)
		ORDER BY Position DESC

		OPEN TestCursor
		FETCH TestCursor
		INTO @table_id, @no_of_rows

		DECLARE @start DATETIME
		DECLARE @end   DATETIME

		WHILE @@FETCH_STATUS = 0
		BEGIN
			SELECT @table_name = Name FROM Tables
			WHERE TableID = CONVERT(INT, @table_id)

			SET @start = GETDATE()
			EXEC multiple_rows_insertion @table_name, @no_of_rows
			SET @end = GETDATE()

			INSERT INTO TestRunTables (TestRunID, TableID, StartAt, EndAt)
			VALUES (@run_id, @table_id, @start, @end)

			FETCH TestCursor
			INTO @table_id, @no_of_rows
		END

		CLOSE TestCursor
		DEALLOCATE TestCursor

		
		/* ------------------ EVALUATE VIEWS ------------------ */
		
		DECLARE @view_name NVARCHAR(MAX) = '';

		DECLARE TestCursor CURSOR FOR 
		SELECT ViewID FROM TestViews
		WHERE TestID = CONVERT(INT, @test_id)

		OPEN TestCursor
		FETCH TestCursor
		INTO @view_id;
	
		WHILE @@FETCH_STATUS = 0
		BEGIN
			SELECT @view_name = Name FROM Views
			WHERE ViewID = CONVERT(INT, @view_id);

			SET @query = 'SELECT * FROM ' + @view_name;
		
			SET @start = GETDATE();
			EXEC sp_executesql @query;
			SET @end = GETDATE();

			INSERT INTO TestRunViews (TestRunID, ViewID, StartAt, EndAt)
			VALUES (@run_id, @view_id, @start, @end);

			FETCH TestCursor
			INTO @view_id;
		END
	
		CLOSE TestCursor;
		DEALLOCATE TestCursor;


		
		UPDATE TestRuns
		SET EndAt = GETDATE()
		WHERE Description = (SELECT Name FROM Tests WHERE TestID = @test_id and StartAt = @start_test)

END
GO

EXEC run_test 5
SELECT * FROM Styles
SELECT * FROM Coach
SELECT * FROM Nutrition

SELECT * FROM TestRuns
SELECT * FROM TestRunTables
SELECT * FROM TestRunViews

SELECT * FROM TestTables

