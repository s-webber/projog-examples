package com.example;

import java.io.File;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.core.term.Term;
import org.projog.core.term.TermType;

/**
 * @see https://github.com/s-webber/projog/issues/143
 */
public class Issue143 {
   public static void main(String[] args) {
      // create a new Projog instance with an ProjogListener that will receive notification of debug events
      CallStackListener listener = new CallStackListener();
      CallStack stack = listener.getCallstack();
      Projog p = new Projog(listener);

      // consult file containing rules and facts
      p.consultFile(new File("src/main/resources/Issue143.pl"));

      // call "trace" built-in predicate to enable debugging
      // see: http://projog.org/prolog-debugging.html
      p.executeOnce("trace.");

      System.out.println("FIRST QUERY");
      QueryResult r1 = p.createStatement("testRule(X, Y).").executeQuery();
      while (r1.next()) {
         System.out.println("---------------------------------------");
         System.out.println("ANSWER: X = " + r1.getTerm("X") + " Y = " + r1.getTerm("Y"));
         log(stack);
      }

      // NOTE: need to call clear() on stack so it is reset before making the next query
      stack.clear();

      System.out.println("SECOND QUERY");
      QueryResult r2 = p.createStatement("testRule(roomA, roomC).").executeQuery();
      while (r2.next()) {
         System.out.println("---------------------------------------");
         log(stack);
      }
   }

   private static void log(final CallStack stack) {
      boolean first = true;
      for (CallStack.Element e : stack) {
         if (first) {
            System.out.println("Input: " + e.getFormattedInput());
            System.out.println();
            first = false;
         }

         System.out.println("Matched " + e.getPredicateKey());
         System.out.println("Clause: " + e.getFormattedClause());
         System.out.println("Fact?   " + isFact(e));
         System.out.println("Output: " + e.getFormattedOutput());
         System.out.println();
      }
   }

   /**
    * Returns {@code true} if the clause of the given {@code CallStack.Element} is a fact, rather than a rule.
    * <p>
    * Assumes that a clause is a fact if its antecedant is "true". For example, both of these clauses would be
    * considered as facts: "x(y)" and "x(y) :- true". Note that this behaviour may not necessarily match people's
    * expectations - for example when the clause is the first clause of a tail-recursive predicate. e.g.:
    * </p>
    * <pre>
    * list([]).
    * list([X|Xs]) :- list(Xs).
    * </pre>
    * <p>
    * then {@code true} will be returned from this method when the given element refers to the first clause - i.e.
    * {@code list([])}. Some people may instead expect this method to return {@code false} in that circumstance.
    * </p>
    */
   private static boolean isFact(CallStack.Element e) {
      Term antecedant = e.getClauseModel().getAntecedent();
      return antecedant.getType() == TermType.ATOM && antecedant.getName().equals("true");
   }
}
