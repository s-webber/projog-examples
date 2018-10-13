male(homer).
male(bart).
male(ned).
male(rod).
male(todd).

female(marge).
female(lisa).
female(maggie).
female(maude).

father(homer,bart).
father(homer,lisa).
father(homer,maggie).
father(ned,rod).
father(ned,todd).

mother(marge,bart).
mother(marge,lisa).
mother(marge,maggie).
mother(maude,rod).
mother(maude,todd).

parents(F,M,C) :- father(F,C), mother(M,C).

siblings(A,B) :- parents(F,M,A), parents(F,M,B), \+ A==B.

brother(A,B) :- siblings(A,B), male(B).

sister(A,B) :- siblings(A,B), female(B).
