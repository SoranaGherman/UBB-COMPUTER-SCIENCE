#ifndef LISTA_H
#define LISTA_H


//tip de data generic (pentru moment este intreg)
typedef int TElem;

//referire a structurii Nod;
struct Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod* PNod;

typedef struct Nod {
	TElem e;
	PNod urm;
};

typedef struct {
	//prim este adresa primului Nod din lista
	PNod _prim;
} Lista;

//operatii pe lista - INTERFATA
void transformListinSet(PNod p, PNod prev, Lista& l); // given a list, we transform it in a set(no duplicated values and the order does not matter)
bool findIfElemAppear(PNod nxtNode, TElem curNodeVal); //find if the current element appears in the rest of the list starting with the next index after it
void unionOfTwoSets(PNod pL, PNod pG, Lista& G); // for each elem in L we look through the elems of G and if not found we add
											   // them after the head.

//crearea unei liste din valori citite pana la 0
Lista creare();
//tiparirea elementelor unei liste
void tipar(Lista l);
// destructorul listei
void distruge(Lista l);

#endif
