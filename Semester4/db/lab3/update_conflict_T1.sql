USE Swimming_competition;

--DELETE FROM Team;

--INSERT INTO Team(tid, name) VALUES (1, 'Casa Ema'), (2, 'Aqua01'), (3, 'Municipal Baia-Mare');

--SELECT * FROM Team;

-- Update conflict T1

WAITFOR DELAY '00:00:05'

BEGIN TRANSACTION
SELECT * FROM Team WHERE tid = 1

UPDATE Team SET name = 'Casa Ema Bistrita' WHERE tid = 1

WAITFOR DELAY '00:00:05'

COMMIT TRANSACTION

-- ALTER DATABASE Swimming_competition SET ALLOW_SNAPSHOT_ISOLATION ON



