#include "User.h"

User::User(std::string name) :
	name{ name }
{
}

std::string User::getName()
{
	return this->name;
}

std::ostream& operator<<(std::ostream& os, const User& u)
{
	return os << u.name << "\n";
}

std::istream& operator>>(std::istream& is, User& u)
{
	return is >> u.name;
}
