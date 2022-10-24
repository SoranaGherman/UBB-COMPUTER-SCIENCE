#include "List.h"
#include <iostream>

using namespace std;


PNod creare_rec() {
    TElem x;
    cout << "x=";
    cin >> x;
    if (x == 0)
        return NULL;
    else {
        PNod p = new Nod();
        p->e = x;
        p->urm = creare_rec();
        return p;
    }
}

void transformListinSet(PNod p, PNod prev, Lista& l)
{
    if (p != NULL)
    {
        if (findIfElemAppear(p->urm, p->e) == true) // if we found the current node in the following nodes, we exclude it
                                                    // hence, we connect the previous node with the following node
        {
            if (prev != NULL)
            {
                prev->urm = p->urm;
                transformListinSet(p->urm, p, l);
            }
            else if (prev == NULL)
            {
                l._prim = p->urm;
                transformListinSet(p->urm, NULL, l); // we are not sure that the new head does not appear in the following nodes
                                                     // it is possible to modify the new head also
            }
        }
        else
            transformListinSet(p->urm, p, l);
    }

}

bool findIfElemAppear(PNod nxtNode, TElem currNodeVal)
{
    if (nxtNode == NULL) return false; // if we are at the end of the list, it means the element has not been found
    if (nxtNode->e == currNodeVal) return true; //the element appears, hence it is no need to go forward
    return findIfElemAppear(nxtNode->urm, currNodeVal);

}

void unionOfTwoSets(PNod pL, PNod pG, Lista& G)
{
    if (pL != NULL)
    {
        if (findIfElemAppear(pG, pL->e) == false) 
        {
            PNod aux = new Nod();
            aux = pL->urm;
            pL->urm = G._prim->urm;
            G._prim->urm = pL;
            unionOfTwoSets(aux, pG, G);
        }
        
        unionOfTwoSets(pL->urm, pG, G);
        
    }
}

Lista creare() {
    Lista l;
    l._prim = creare_rec();
    return l;
}

void tipar_rec(PNod p) {
    if (p != NULL) {
        cout << p->e << " ";
        tipar_rec(p->urm);
    }
}

void tipar(Lista l) {
    tipar_rec(l._prim);
}

void distrug_rec(PNod p) {
    if (p != NULL) {
        distrug_rec(p->urm);
        delete p;
    }
}

void distrug(Lista l) {
    //se elibereaza memoria alocata nodurilor listei
    distrug_rec(l._prim);
}

