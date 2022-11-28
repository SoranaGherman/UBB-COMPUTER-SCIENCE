#include "SortedMap.h"
#include <exception>
using namespace std;

struct SortedMap::DLLNode {
	TElem value;
	PNode next;
	PNode previous;
	DLLNode(TElem e, PNode n, PNode p);
};

SortedMap::DLLNode::DLLNode(TElem e, PNode n, PNode p) {
	value = e;
	next = n;
	previous = p;
}

SortedMap::SortedMap(Relation r) {
	this->r = r;
	this->head = nullptr;
	this->tail = nullptr;
}

//O(nr. of elem)
TValue SortedMap::add(TKey k, TValue v) {
	TElem e;
	e.first = k;
	e.second = v;

	// if the key already exists, we only change the previous value
	PNode p = this->head;

	//When empty
	if (p == nullptr) {
		PNode node = new DLLNode(e, nullptr, nullptr);
		this->head = node;
		this->tail = node;
		return NULL_TVALUE;
	}

	while (p != nullptr)
	{
		if (p->value.first == k)
		{
			TValue old = p->value.second;
			p->value.second = v;
			return old;
		}
		p = p->next;
	}

	// if we reach these lines, it means that the key was not already added

	p = head;
	while (p != nullptr && this->r(p->value.first, k))
		p = p->next;

	if (p == head) {
		PNode node = new DLLNode(e, p, nullptr);
		node->next->previous = node;
		head = node;
		return NULL_TVALUE;
	}

	//true daca p e null
	if (!p) {
		PNode node = new DLLNode(e, nullptr, tail);
		node->previous->next = node;
		tail = node;
		return NULL_TVALUE;
	}

	PNode node = new DLLNode(e, p, p->previous);
	node->next->previous = node;
	node->previous->next = node;

	return NULL_TVALUE;
}

//O(nr of elem)
TValue SortedMap::search(TKey k) const {
	PNode p = head;
	while (p != nullptr)
	{
		if (p->value.first == k)
			return p->value.second;
		p = p->next;
	}
	return NULL_TVALUE;
}

//O(n)
TValue SortedMap::remove(TKey k) {
	
	PNode p = head;
	
	if (p != nullptr)
	{
		if (p->value.first == k)
		{
			TValue oldValue = p->value.second;
			head = p->next;
			delete p;
			return oldValue;
		}

		while (p->next != nullptr) {
			if (p->next->value.first == k)
			{
				TValue oldValue = p->next->value.second;
				PNode pn = p->next;
				pn->previous->next = pn->next;

				if (pn->next)
					pn->next->previous = pn->previous;
				else
					tail = p;

				delete pn;
				return oldValue;
			}
			p = p->next;
		}
	}
	return NULL_TVALUE;
}

//O(n)
int SortedMap::size() const {
	int count = 0;
	PNode p = head;

	while (p != nullptr)
	{
		count++;
		p = p->next;
	}
	return count;
}

//O(1)
bool SortedMap::isEmpty() const {
	if (head == nullptr) return true;
	return false;
}

//O(n)
std::vector<TValue> SortedMap::valueBag() const
{
	PNode p = head;
	std::vector<TValue> valueBag;
	while (p != nullptr) {
		valueBag.push_back(p->value.second);
		p = p->next;
	}
	return valueBag;
}

//O(1)
SMIterator SortedMap::iterator() const {
	return SMIterator(*this);
}

SortedMap::~SortedMap() {
	while (head != nullptr) {
		PNode p = head;
		head = head->next;
		delete p;
	}
}


SMIterator::SMIterator(const SortedMap& m) : map(m) {
	currentElement = m.head;
}

void SMIterator::first() {
	currentElement = map.head;
}

void SMIterator::next() {
	if (currentElement == nullptr) throw exception();
	currentElement = currentElement->next;
}

void SMIterator::previous() {
	if (currentElement == nullptr) throw exception();
	currentElement = currentElement->previous;
}

bool SMIterator::valid() const {
	return currentElement != nullptr;
}

TElem SMIterator::getCurrent() const {
	if (currentElement == nullptr) throw exception();
	return currentElement->value;
}