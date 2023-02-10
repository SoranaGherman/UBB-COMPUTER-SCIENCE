use eLearning

CREATE TABLE Instructor
(
	first_name VARCHAR(20) UNIQUE,
	last_name VARCHAR(20) UNIQUE,
	experience INT,
	PRIMARY KEY(first_name, last_name)
);

CREATE TABLE Course
(
	cid INT PRIMARY KEY,
	title VARCHAR(20),
	language VARCHAR(20),
	price INT,
	discount INT,
	first_name VARCHAR(20),
	last_name VARCHAR(20),
	FOREIGN KEY(first_name, last_name) REFERENCES Instructor(first_name, last_name)
);

CREATE TABLE Topic
(
	tid INT PRIMARY KEY,
	name VARCHAR(20)
);

CREATE TABLE CourseTopic
(
	cid INT REFERENCES Course(cid),
	tid INT REFERENCES Topic(tid),
	PRIMARY KEY(cid, tid)
);

CREATE TABLE Lecture
(
	lid INT PRIMARY KEY,
	title VARCHAR(20),
	cid INT REFERENCES Course(cid)
);

CREATE TABLE Resources
(
	r_url VARCHAR(30) PRIMARY KEY,
	lid INT,
	FOREIGN KEY(lid) REFERENCES Lecture(lid)
);
GO


CREATE OR ALTER PROCEDURE deleteCourses(@f_n VARCHAR(20), @l_n VARCHAR(20))
AS
BEGIN
		IF EXISTS(SELECT * FROM Instructor WHERE first_name = @f_n AND last_name = @l_n)
			DELETE FROM COURSE WHERE first_name = @f_n AND last_name = @l_n
		ELSE
			raiserror('THE INSTRUCTOR WITH THE GIVEN NAME DOES NOT EXIST!', 12, 1)
END
GO

CREATE OR ALTER VIEW showName
AS
	SELECT C.first_name, C.last_name
	FROM Course C INNER JOIN CourseTopic CT ON C.cid = CT.cid
	WHERE CT.tid IN (SELECT T.tid FROM Topic T WHERE CT.tid = T.tid and T.name = 'Deep Learning')
	GROUP BY C.first_name, C.last_name, C.cid
	HAVING COUNT(*) > 2
	INTERSECT
	SELECT C.first_name, C.last_name
	FROM COURSE C INNER JOIN Lecture L ON C.cid = L.cid
	WHERE (SELECT COUNT(*) FROM Lecture L1 WHERE L1.cid = C.cid) > 10
	GROUP BY C.first_name, C.last_name, C.cid
	HAVING COUNT(*) > 1

GO

CREATE OR ALTER FUNCTION instructorsCourses(@c INT, @p INT)
RETURNS TABLE
AS
	RETURN
			SELECT I.first_name, I.last_name
			FROM Instructor I INNER JOIN Course C ON I.first_name = C.first_name AND I.last_name = C.last_name
			WHERE C.price > @p
			GROUP BY I.first_name, I.last_name, C.cid
			HAVING COUNT(*) > @c
GO
	
	