#include "Service.h"
#include <random>

Service::Service(Repository<Question>* questions, Repository<Answer>* answers) :
    questions{ questions }, answers{ answers }
{
}

std::vector<Question> Service::getAllQuest()
{
    return this->questions->getAll();
}

std::vector<Answer> Service::getAllAnsw()
{
    return this->answers->getAll();
}

void Service::addQuestion(std::string text, User u)
{
    std::vector<Question> q = this->questions->getAll();
    int ok = 0, id;
    while (!ok)
    {
        id = rand() % 50 + 10;
        ok = 1;
        for (auto obj : q)
            if (id == obj.getId() && ok) ok = 0;
               
    }

    Question question{id, text, u.getName()};
    this->questions->addElement(question);
    this->notify();

}

void Service::addAnswer(int qId, std::string text, User u)
{
    std::vector<Answer> a = this->answers->getAll();
    int ok = 0, id;
    while (!ok)
    {
        id = rand() % 50 + 1;
        ok = 1;
        for (auto obj : a)
            if (id == obj.getId() && ok) ok = 0;

    }

    Answer answ{id, qId, text, u.getName(), 0};
    this->answers->addElement(answ);
    this->notify();

}
