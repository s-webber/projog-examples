package com.example;

import org.projog.core.predicate.AbstractPredicateFactory;
import org.projog.core.predicate.Predicate;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;
import org.projog.core.term.TermUtils;

/* TEST
 ?- pj_add_predicate(split/2, 'com.example.RetryablePredicateExample').

 %?- split('dog,cat,bird', X)
 % X=dog
 % X=cat
 % X=bird

 %?- split('b,a,n,a,n,a', a)
 %YES
 %YES
 %YES

 %TRUE_NO split('a,b,c', a)
 %TRUE split('a,b,c', c)
 %FAIL split('a,b,c', z)

 %?- split(X, a)
 %ERROR Expected an atom but got: VARIABLE with value: X

 %?- split(42, a)
 %ERROR Expected an atom but got: INTEGER with value: 42

 %TRUE split('3,6,9', '9')
 %FAIL split('3,6,9', 9)
 */
/**
 * <code>split(X,Y)</code> - compares Y to each of the comma-separated values represented by X.
 *
 * @see SingleResultPredicateExample
 */
public class RetryablePredicateExample extends AbstractPredicateFactory {
   @Override
   public Predicate getPredicate(Term arg1, Term arg2) {
      String csv = TermUtils.getAtomName(arg1);
      String[] split = csv.split(",");
      return new RetryablePredicate(split, arg2);
   }

   private static class RetryablePredicate implements Predicate {
      private final String[] split;
      private final Term target;
      private int idx;

      RetryablePredicate(String[] split, Term target) {
         this.split = split;
         this.target = target;
      }

      @Override
      public boolean evaluate() {
         while (idx < split.length) {
            target.backtrack();
            String next = split[idx++];
            if (target.unify(new Atom(next))) {
               return true;
            }
         }

         return false;
      }

      @Override
      public boolean couldReevaluationSucceed() {
         return idx < split.length;
      }
   }
}
