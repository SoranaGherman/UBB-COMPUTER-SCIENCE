USE Swimming_competition;

--DELETE FROM Team;

--INSERT INTO Team(tid, name) VALUES (1, 'Casa Ema'), (2, 'Aqua01'), (3, 'Municipal Baia-Mare');

--SELECT * FROM Team;

-- Non-repeatable read Transaction 1

BEGIN TRANSACTION
WAITFOR DELAY '00:00:10'
UPDATE Team SET name = 'Casa Ema Bistrita' WHERE tid = 1
COMMIT TRANSACTION