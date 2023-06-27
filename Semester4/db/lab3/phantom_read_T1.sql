use Swimming_competition;

--DELETE FROM Team;

--INSERT INTO Team(tid, name) VALUES (1, 'Casa Ema'), (2, 'Aqua01'), (3, 'Municipal Baia-Mare');

--SELECT * FROM Team;

-- Phantom read Transaction 1

BEGIN TRANSACTION
WAITFOR DELAY '00:00:10'
INSERT INTO Team (tid, name)
VALUES (4, 'Steaua Bucuresti')
COMMIT TRANSACTION