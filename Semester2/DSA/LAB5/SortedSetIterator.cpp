#include "SortedSetIterator.h"
#include <exception>

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
{
    this->myStack = new Node * [multime.nrOfElems];

    Node* node = multime.root;
    this->currentPosition = -1;
    while (node != nullptr)
    {
        this->currentPosition++;
        this->myStack[this->currentPosition] = node;

        node = node->left;
    }
    if (this->currentPosition >= 0)
        this->currentNode = this->myStack[this->currentPosition];
    else {
        this->currentNode = nullptr;
    }
}
//O(n)

void SortedSetIterator::first() {

    Node* node = multime.root;
    this->currentPosition = -1;

    while (node != nullptr)
    {
        this->currentPosition++;
        this->myStack[this->currentPosition] = node;
        node = node->left;
    }

    if (this->currentPosition >= 0)
        this->currentNode = this->myStack[this->currentPosition];
    else {
        currentNode = nullptr;
    }
}
//O(n)

void SortedSetIterator::next() {
    if (!this->valid())
        throw exception();

    Node* node = this->myStack[this->currentPosition];
    this->currentPosition--;

    if (node->right != nullptr)
    {
        node = node->right;
        while (node != nullptr)
        {
            this->currentPosition++;
            this->myStack[this->currentPosition] = node;
            node = node->left;
        }
    }

    if (this->currentPosition >= 0)
        this->currentNode = this->myStack[this->currentPosition];
    else
        this->currentNode = nullptr;
}
//O(n)

TElem SortedSetIterator::getCurrent()
{
    if (!this->valid())
        throw exception();
    return this->currentNode->data;
}
//Theta(1)

bool SortedSetIterator::valid() const {
    if (this->currentNode == nullptr)
        return false;
    return true;
}
//Theta(1)
