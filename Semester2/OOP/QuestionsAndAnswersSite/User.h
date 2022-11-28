#pragma once

#include <iostream>

class User
{
public:
	User() = default;
	User(std::string name);
	~User() =  default;

	std::string getName();

	friend std::ostream& operator<<(std::ostream& os, const User& u);
	friend std::istream& operator>>(std::istream& is,  User& u);

private:
	std::string name;
};

