#pragma once
#include "SortedSet.h"

//DO NOT CHANGE THIS PART
class SortedSetIterator
{
	friend class SortedSet;
private:
	const SortedSet& multime;
	Node** myStack;
	Node* currentNode;
	int currentPosition;
	SortedSetIterator(const SortedSet& m);

public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;
};

