#pragma once

#include <iostream>

class Answer
{
public:
	Answer() = default;
	Answer(int id, int idQ, std::string name, std::string text, int nrVotes);
	~Answer() = default;


	int getId();
	int getIdQ();
	std::string getName();
	std::string getText();
	int getNrVotes();

	friend std::ostream& operator<<(std::ostream& os, const Answer& a);
	friend std::istream& operator>>(std::istream& is, Answer& a);

private:
	int id;
	int idQ;
	std::string name;
	std::string text;
	int nrVotes;
};

