SET TRAN ISOLATION LEVEL SNAPSHOT

BEGIN TRAN
SELECT * FROM Team WHERE tid = 1
WAITFOR DELAY '00:00:05'
-- T1 has updated and has a lock on the table
-- T2 will be blocked when trying to update the table
UPDATE Team SET name = 'Casa Ema Bistrita R' WHERE tid = 1

COMMIT TRANSACTION
