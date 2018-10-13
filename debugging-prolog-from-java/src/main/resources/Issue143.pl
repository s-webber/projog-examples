location(fridge, roomA, home).
location(microwave , roomA, home).
location(oven, roomA, home).
location(computer, roomC, home).
location(radio, roomC, home).
location(modem, roomC, home).
location(m1, roomD, home).
location(m2, roomD, home).
location(m3, roomD, home).
location(m4, roomD, home).
location(airCond, roomB, home).
test(e1, roomA, roomB).
test(e1, roomC, roomB).
test(e2, roomD, roomB).
test(e3, roomE, roomB).
% test(e4, roomA, roomC). % line 16
testRule(A, B) :- test(Electricity, A, B).
testRule(A, B) :- not_test(Electricity, A, B), location(_, A, home), location(_, B, home).

not_test(Electricity, A, B) :- \+ test(Electricity, A, B).
