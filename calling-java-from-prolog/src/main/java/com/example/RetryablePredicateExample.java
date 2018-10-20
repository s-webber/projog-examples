package com.example;

import org.projog.core.function.AbstractPredicate;
import org.projog.core.function.AbstractPredicateFactory;
import org.projog.core.term.Atom;
import org.projog.core.term.Term;
import org.projog.core.term.TermUtils;

/* TEST
 ?- pj_add_predicate(split/2, 'com.example.RetryablePredicateExample').

 %QUERY split('dog,cat,bird', X)
 %ANSWER X=dog
 %ANSWER X=cat
 %ANSWER X=bird

 %QUERY split('b,a,n,a,n,a', a)
 %ANSWER/
 %ANSWER/
 %ANSWER/

 %TRUE_NO split('a,b,c', a)
 %TRUE split('a,b,c', c)
 %FALSE split('a,b,c', z)

 %QUERY split(X, a)
 %ERROR Expected an atom but got: NAMED_VARIABLE with value: X

 %QUERY split(42, a)
 %ERROR Expected an atom but got: INTEGER with value: 42

 %TRUE split('3,6,9', '9')
 %FALSE split('3,6,9', 9)
 */
/**
 * <code>split(X,Y)</code> - compares Y to each of the comma-separated values represented by X.
 *
 * @see SingletonPredicateExample
 */
public class RetryablePredicateExample extends AbstractPredicateFactory {
   @Override
   public Predicate getPredicate(Term arg1, Term arg2) {
      String csv = TermUtils.getAtomName(arg1);
      String[] split = csv.split(",");
      return new Predicate(split, arg2);
   }

   private static class Predicate extends AbstractPredicate {
      private final String[] split;
      private final Term target;
      private int idx;

      Predicate(String[] split, Term target) {
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
