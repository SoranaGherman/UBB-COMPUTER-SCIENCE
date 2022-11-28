#include "SourceFileDomain.h"

SourceFile::SourceFile(std::string name, std::string status, std::string creator, std::string reviewer) :
    name{ name }, status{ status }, creator{ creator }, reviewer{ reviewer }
{
}

std::string SourceFile::getName()
{
    return this->name;
}

std::string SourceFile::getStatus()
{
    return this->status;
}

std::string SourceFile::getCreator()
{
    return this->creator;
}

std::string SourceFile::getReviewer()
{
    return this->reviewer;
}

void SourceFile::setStatus()
{
    this->status = "revised";
}

void SourceFile::setReviewer(std::string name)
{
    this->reviewer = name;
}

std::ostream& operator<<(std::ostream& os, const SourceFile& sf)
{
    return os << sf.name << " " << sf.status << " " << sf.creator << " " << sf.reviewer << "\n";
}

std::istream& operator>>(std::istream& is, SourceFile& sf)
{
    return is >> sf.name >> sf.status >> sf.creator >> sf.reviewer;
}
