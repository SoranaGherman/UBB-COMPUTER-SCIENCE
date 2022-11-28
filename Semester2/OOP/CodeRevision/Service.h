#pragma once

#include "Repository.h"
#include "Observer.h"


class Service: public Subject
{
public:
	Service() = default;
	Service(Repository<Programmer>* programmers, Repository<SourceFile>* sourceFiles);
	~Service() = default;

	std::vector<SourceFile> getAllSourceFiles();
	std::vector<Programmer> getAllProg();
	std::vector<SourceFile> getAllSortedByName();
	void addFile(std::string fileName, Programmer p);
	void updateStatus(int index, Programmer p);

private:
	Repository<Programmer>* programmers;
	Repository<SourceFile>* sourceFiles;
};

