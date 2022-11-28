#pragma once

#include <iostream>

class Question
{
public:
	Question() = default;
	Question(int id, std::string text, std::string name);
	~Question() =  default;

	int getId();
	std::string getText();
	std::string getName();


	friend std::ostream& operator<<(std::ostream& os, const Question& q);
	friend std::istream& operator>>(std::istream& is, Question& q);

private:
	int id;
	std::string text;
	std::string name;
};

