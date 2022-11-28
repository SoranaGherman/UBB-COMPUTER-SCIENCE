#pragma once

#include "ProgrammerDomain.h"
#include "SourceFileDomain.h"

#include <vector>
#include <algorithm>
#include <fstream>

template<typename T>
class Repository
{
public:
	Repository() = default;
	Repository(std::string filePath);
	~Repository() = default;

	std::vector<T> getAll();

	void addFile(T& fileName);
	int getSize();
	void updateStatus(int index);
	void updateReviewer(int index, std::string rev);
	void updateRF(int index);

private:
	std::vector<T> data;
	std::string filePath;
	void writeToFile();
	void readFromFile();
};

template<typename T>
inline Repository<T>::Repository(std::string filePath):
	filePath{filePath}
{
	this->readFromFile();
}

template<typename T>
inline std::vector<T> Repository<T>::getAll()
{
	return this->data;
}

template<typename T>
inline void Repository<T>::addFile(T& fileName)
{
	this->data.push_back(fileName);
	this->writeToFile();
}

template<typename T>
inline int Repository<T>::getSize()
{
	return this->data.size();
}


inline void Repository<SourceFile>::updateStatus(int index)
{
	this->data[index].setStatus();
	this->writeToFile();
}


inline void Repository<SourceFile>::updateReviewer(int index, std::string rev)
{
	this->data[index].setReviewer(rev);
	this->writeToFile();
}

inline void Repository<Programmer>::updateRF(int index)
{
	this->data[index].setNrOfRF();
	this->writeToFile();
}

template<typename T>
inline void Repository<T>::writeToFile()
{
	std::ofstream outputFile;
	outputFile.open(this->filePath, std::ios::out);

	for (auto obj : this->data)
		outputFile << obj;

	outputFile.close();
}

template<typename T>
inline void Repository<T>::readFromFile()
{
	std::ifstream inputFile;
	inputFile.open(this->filePath, std::ios::in);

	T elem;
	while (inputFile >> elem)
		this->data.push_back(elem);

	inputFile.close();
}
