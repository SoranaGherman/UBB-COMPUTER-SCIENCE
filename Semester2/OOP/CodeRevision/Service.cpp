#include "Service.h"

Service::Service(Repository<Programmer>* programmers, Repository<SourceFile>* sourceFiles) :
    programmers{ programmers }, sourceFiles{ sourceFiles }
{
}

std::vector<SourceFile> Service::getAllSourceFiles()
{
    return this->sourceFiles->getAll();
}

std::vector<Programmer> Service::getAllProg()
{
    return this->programmers->getAll();
}

std::vector<SourceFile> Service::getAllSortedByName()
{
    std::vector<SourceFile> sortedData = this->sourceFiles->getAll();
    std::sort(sortedData.begin(), sortedData.end(), [](SourceFile& e1, SourceFile& e2) {return e1.getName() < e2.getName();});
    return sortedData;
}

void Service::addFile(std::string fileName, Programmer p)
{
    SourceFile sf(fileName, "not_revised", p.getName(), "None");
    this->sourceFiles->addFile(sf);
    this->notify();
}

void Service::updateStatus(int index, Programmer p)
{
    this->sourceFiles->updateStatus(index);
    std::string name = p.getName();
    this->sourceFiles->updateReviewer(index, name);

    int i = 0;
    std::vector<Programmer> data = this->programmers->getAll();
    for (auto obj : data)
    {
        if (obj.getName() == name)
            break;
        i++;
    }

    this->programmers->updateRF(i);

    this->notify();
}
