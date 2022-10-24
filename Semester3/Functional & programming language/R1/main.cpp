#include "List.h"
#include <iostream>

int main()
{
	//for point a)

	Lista l;
	std::cout << "List for point a)\n";
	l = creare();
	transformListinSet(l._prim, NULL, l);
	tipar(l);

	//for point b)

	Lista L, G;
	std::cout << "First for point b)\n";
	L = creare();
	std::cout << "Second set for point b)\n";
	G = creare();
	unionOfTwoSets(L._prim, G._prim, G);
	tipar(G);

}
