#pragma once

#include "Repository.h"
#include "Observer.h"

class Service : public Subject
{
public:
	Service(Repository<Question>* questions, Repository<Answer>* answers);
	~Service() = default;

	std::vector<Question> getAllQuest();
	std::vector<Answer> getAllAnsw();

	void addQuestion(std::string text, User u);
	void addAnswer(int qId, std::string text, User U);

private:
	Repository<Question>* questions;
	Repository<Answer>* answers;
};

