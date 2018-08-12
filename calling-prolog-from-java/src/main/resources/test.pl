test(a,1).
test(b,2).
test(c,3).
test(d,4).
test(e,5).
test(f,6).
test(g,7).
test(h,8).
test(i,9).

testRule(X) :- test(X, Y), Y mod 2 =:= 0.
