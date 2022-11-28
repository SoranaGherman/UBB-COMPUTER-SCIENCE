#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r) {
	root = nullptr;
	rel = r;
	this->nrOfElems = 0;
}

//Theta(1) if the binary tree is empty
//Theta(h) h the height
//Overall O(h)
bool SortedSet::add(TComp elem) {
	
	Node* current = root;
	Node* parent = nullptr;

	while (current != nullptr)
	{
		parent = current;
		if (current->data == elem) return false;
		if (this->rel(current->data, elem))
			current = current->left;
		else
			current = current->right;
	}

	Node* newNode = new Node{ elem };

	if (root == nullptr)
		root == newNode;
	else if (this->rel(parent->data, elem))
		parent->left = newNode;
	else
		parent->right = newNode;

	nrOfElems++;

	return true;
}


bool SortedSet::remove(TComp e)
{
	return false;
}

//BC:Theta(1)
//WC:Theta(h), where h is the height of the binary tree
//Overall: O(h)
bool SortedSet::search(TElem elem) const
{
	Node* current= this->root;
	if (current == nullptr) return false;

	if (current->data == elem) return true;

	while (current != nullptr)
	{
		if (current->data == elem) return true;
		
		if (this->rel(current->data, elem)) current = current->left;
		else current = current->right;
	}

	return false;
}


int SortedSet::size() const {
	
	return this->nrOfElems;
}



bool SortedSet::isEmpty() const {
	if (this->root == nullptr) return true;
	return false;
}

SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}


SortedSet::~SortedSet() {
	//TODO - Implementation
}


