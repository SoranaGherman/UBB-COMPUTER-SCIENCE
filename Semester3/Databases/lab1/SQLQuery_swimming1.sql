USE Swimming_competition;

CREATE TABLE Team(
	tid INT NOT NULL PRIMARY KEY(tid),
	name VARCHAR(50),
);

CREATE TABLE Category(
	catid INT NOT NULL PRIMARY KEY(catid),
	name INT
);

CREATE TABLE SWIMMER(
	sid INT NOT NULL PRIMARY KEY(sid),
	tid INT,
	catid INT,
	name VARCHAR(50),
	birth_date DATE,
	county VARCHAR(50),
	FOREIGN KEY(tid) REFERENCES Team(tid) ON DELETE CASCADE,
	FOREIGN KEY(catid) REFERENCES Category(catid) ON DELETE CASCADE
);

CREATE TABLE Coach(
	coid INT NOT NULL PRIMARY KEY(coid),
	tid INT,
	name VARCHAR(50),
	FOREIGN KEY(tid) REFERENCES Team(tid) ON DELETE CASCADE
);

CREATE TABLE Laps(
	lid INT NOT NULL PRIMARY KEY(lid),
	length INT
);

CREATE TABLE Styles(
	stid INT NOT NULL PRIMARY KEY(stid),
	name VARCHAR(50)
);

CREATE TABLE Swimming_comp(
	swc INT NOT NULL PRIMARY KEY(swc),
	lid INT,
	stid INT,
	FOREIGN KEY(lid) REFERENCES Laps(lid) ON DELETE CASCADE,
	FOREIGN KEY(stid) REFERENCES Styles(stid) ON DELETE CASCADE
);

CREATE TABLE Register(
	tid INT,
	swc INT,
	FOREIGN KEY(tid) REFERENCES Team(tid) ON DELETE CASCADE,
	FOREIGN KEY(swc) REFERENCES Swimming_comp(swc) ON DELETE CASCADE
);

CREATE TABLE Fan(
	fid INT NOT NULL PRIMARY KEY(fid),
	name VARCHAR(50)
);


CREATE TABLE Fan_of_Swimmer(
	sid INT,
	fid INT,
	FOREIGN KEY(sid) REFERENCES Swimmer(sid) ON DELETE CASCADE,
	FOREIGN KEY(fid) REFERENCES Fan(fid) ON DELETE CASCADE
);

