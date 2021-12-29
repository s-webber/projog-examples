package com.example;

import static org.projog.core.term.TermUtils.getAtomName;

import org.projog.core.predicate.AbstractSingleResultPredicate;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;

/* TEST
 ?- pj_add_predicate(uppercase/2, 'com.example.SingleResultPredicateExample').

 %TRUE uppercase('hello, world!', 'HELLO, WORLD!')
 %TRUE uppercase('hello, everyone!', 'HELLO, EVERYONE!')
 %FAIL uppercase('hello, word!', 'HELLO, WORLD!')
 %FAIL uppercase('hello, word!', 42)
 %FAIL uppercase('HELLO, WORLD!', 'hello, world!')

 %?- uppercase('hello, world!', X)
 % X=HELLO, WORLD!

 %?- uppercase(X, 'HELLO, WORLD!')
 %ERROR Expected an atom but got: VARIABLE with value: X
 */
/**
 * <code>uppercase(X,Y)</code> - succeeds if the atom represented by Y is equal to an upper case version of the atom
 * represented by X.
 *
 * @see RetryablePredicateExample
 */
public class SingleResultPredicateExample extends AbstractSingleResultPredicate {
   @Override
   public boolean evaluate(Term term1, Term term2) {
      Atom upperCaseTerm1 = new Atom(getAtomName(term1).toUpperCase());
      return term2.unify(upperCaseTerm1);
   }
}
