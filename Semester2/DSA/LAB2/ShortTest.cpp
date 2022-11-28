#include <assert.h>

#include "SortedMap.h"
#include "SMIterator.h"
#include "ShortTest.h"
#include <exception>
using namespace std;

bool relatie1(TKey cheie1, TKey cheie2) {
	if (cheie1 <= cheie2) {
		return true;
	}
	else {
		return false;
	}
}

void testAll(){
	SortedMap sm(relatie1);
	assert(sm.size() == 0);
	assert(sm.isEmpty());
    sm.add(1,2);
    assert(sm.size() == 1);
    assert(!sm.isEmpty());
    assert(sm.search(1)!=NULL_TVALUE);
    TValue v =sm.add(1,3);
    assert(v == 2);
    assert(sm.search(1) == 3);
    SMIterator it = sm.iterator();
    it.first();
    while (it.valid()){
    	TElem e = it.getCurrent();
    	assert(e.second != NULL_TVALUE);
    	it.next();
    }
    assert(sm.remove(1) == 3);
    assert(sm.isEmpty());

    //tests for returning  vector with all values
    SortedMap sm2(relatie1);
    assert(sm2.size() == 0);
    assert(sm2.isEmpty());
    sm2.add(1, 2);
    assert(sm2.size() == 1);
    sm2.add(3, 4);
    assert(sm2.size() == 2);
    assert(sm2.valueBag()[0] == 2);
    assert(sm2.valueBag()[1] == 4);
    assert(sm2.valueBag().size() == 2);
    sm2.add(4, 5);
    sm2.add(6, 6);
    assert(sm2.valueBag()[2] == 5);
    assert(sm2.valueBag()[3] == 6);
    assert(sm2.valueBag().size() == 4);
    sm2.remove(6);
    assert(sm2.valueBag().size() == 3);
}

