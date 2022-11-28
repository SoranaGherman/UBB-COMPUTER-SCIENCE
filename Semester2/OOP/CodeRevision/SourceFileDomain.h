#pragma once

#include <iostream>

class SourceFile
{
public:
	SourceFile() = default;
	SourceFile(std::string name, std::string status, std::string creator, std::string reviewer);
	~SourceFile() =  default;

	std::string getName();
	std::string getStatus();
	std::string getCreator();
	std::string getReviewer();

	void setStatus();
	void setReviewer(std::string name);

	friend std::ostream& operator<<(std::ostream& os, const SourceFile& sf);
	friend std::istream& operator>>(std::istream& is, SourceFile& sf);

private:
	std::string name;
	std::string status;
	std::string creator;
	std::string reviewer;
};

