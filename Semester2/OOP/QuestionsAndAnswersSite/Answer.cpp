#include "Answer.h"

Answer::Answer(int id, int idQ, std::string name, std::string text, int nrVotes) :
	id{ id }, idQ{ idQ }, name{ name }, text{ text }, nrVotes{ nrVotes }
{
}

int Answer::getId()
{
	return this->id;
}

int Answer::getIdQ()
{
	return this->idQ;
}

std::string Answer::getName()
{
	return this->name;
}

std::string Answer::getText()
{
	return this->text;
}

int Answer::getNrVotes()
{
	return this->nrVotes;
}

std::ostream& operator<<(std::ostream& os, const Answer& a)
{
	return os << a.id << " " << a.idQ << " " << a.name << " " << a.text << " " << a.nrVotes << "\n";
}

std::istream& operator>>(std::istream& is, Answer& a)
{
	return is >> a.id >> a.idQ >> a.name >> a.text >> a.nrVotes;

}
