#pragma once

#include <iostream>
#include <vector>

class Observer
{
public:
	
	virtual void update() = 0;
	~Observer() = default;

private:

};


class Subject
{
public:
	Subject() = default;
	~Subject() = default;

	void notify() { for (auto obj : this->observers) obj->update(); };
	void addObserver(Observer* observer) { this->observers.push_back(observer); };

private:
	std::vector<Observer*> observers;
};

