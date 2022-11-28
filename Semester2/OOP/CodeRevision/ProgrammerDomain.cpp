#include "ProgrammerDomain.h"

Programmer::Programmer(std::string name, int nrOfRF, int nrOfRFMust) :
    name{ name }, nrOfRF{ nrOfRF }, nrOfRFMust{ nrOfRFMust }
{
}

std::string Programmer::getName()
{
    return this->name;
}

int Programmer::getNrOfRF()
{
    return this->nrOfRF;
}

int Programmer::getNrOfRFMust()
{
    return this->nrOfRFMust;
}

void Programmer::setNrOfRF()
{
    this->nrOfRF++;
}

std::ostream& operator<<(std::ostream& os, const Programmer& p)
{
    return os << p.name << " " << p.nrOfRF << " " << p.nrOfRFMust << "\n";
}

std::istream& operator>>(std::istream& is, Programmer& p)
{
    return is >> p.name >> p.nrOfRF >> p.nrOfRFMust;
}
