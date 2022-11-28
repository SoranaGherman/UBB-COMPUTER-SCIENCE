#pragma once

#include <iostream>

class Programmer
{
public:
	Programmer() = default;
	Programmer(std::string name, int nrOfRF, int nrOfRFMust);
	~Programmer() = default;

	std::string getName();
	int getNrOfRF();
	int getNrOfRFMust();

	void setNrOfRF();

	friend std::ostream& operator<<(std::ostream& os, const Programmer& p);
	friend std::istream& operator>>(std::istream& is, Programmer& p);

private:
	std::string name;
	int nrOfRF;
	int nrOfRFMust;
};

