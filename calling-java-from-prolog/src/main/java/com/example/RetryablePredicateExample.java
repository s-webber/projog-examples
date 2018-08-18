package com.example;

import org.projog.core.function.AbstractRetryablePredicate;
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
public class RetryablePredicateExample extends AbstractRetryablePredicate {
   private String[] split;
   private int idx;

   @Override
   public RetryablePredicateExample getPredicate(Term arg1, Term arg2) {
      return new RetryablePredicateExample();
   }

   @Override
   public boolean evaluate(Term arg1, Term arg2) {
      if (split == null) {
         String csv = TermUtils.getAtomName(arg1);
         split = csv.split(",");
      }

      while (idx < split.length) {
         arg2.backtrack();
         String next = split[idx++];
         if (arg2.unify(new Atom(next))) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean couldReEvaluationSucceed() {
      return idx < split.length;
   }
}
