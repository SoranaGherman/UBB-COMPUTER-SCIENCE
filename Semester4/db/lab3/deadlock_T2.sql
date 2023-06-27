use Swimming_competition;

-- Deadlock Transaction 2

-- Solution for the deadlock:
-- SET DEADLOCK_PRIORITY HIGH 

BEGIN TRANSACTION
UPDATE Team SET name='Aqua01' WHERE tid=2
WAITFOR DELAY '00:00:05'
UPDATE Team SET name='Casa Ema' WHERE tid=1
COMMIT TRANSACTION