% Implementation of tfilter/3 taken from:
% https://github.com/meditans/reif/blob/master/prolog/reif.pl

dif(X, Y, T) :-
  =(X, Y, NT),
  non(NT, T).

non(true, false).
non(false, true).

=(X, Y, T) :-
   (  X == Y -> T = true
   ;  X \= Y -> T = false
   ;  T = true, X = Y
   ;  T = false,
      dif(X, Y)
   ).

if_(If_1, Then_0, Else_0) :-
   call(If_1, T),
   (  T == true -> Then_0
   ;  T == false -> Else_0
   ;  nonvar(T) -> throw(error(type_error(boolean,T),
                               type_error(call(If_1,T),2,boolean,T)))
   ;  throw(error(instantiation_error,instantiation_error(call(If_1,T),2)))
   ).

tfilter(C_2, Es, Fs) :-
   i_tfilter(Es, C_2, Fs).

i_tfilter([], _, []).
i_tfilter([E|Es], C_2, Fs0) :-
   if_(call(C_2, E), Fs0 = [E|Fs], Fs0 = Fs),
   i_tfilter(Es, C_2, Fs).
