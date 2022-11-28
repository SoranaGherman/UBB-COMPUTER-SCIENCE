#include "Question.h"

Question::Question(int id, std::string text, std::string name) :
	id{ id }, text{ text }, name{ name }
{
}

int Question::getId()
{
	return this->id;
}

std::string Question::getText()
{
	return this->text;
}

std::string Question::getName()
{
	return this->name;
}

std::ostream& operator<<(std::ostream& os, const Question& q)
{
	return os << q.id << " " << q.text << " " << q.name << "\n";
}

std::istream& operator>>(std::istream& is,  Question& q)
{
	return is >> q.id >> q.name >> q.text;
}
