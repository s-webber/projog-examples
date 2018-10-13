package com.example;

import java.io.File;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;

/**
 * @see https://github.com/s-webber/projog/issues/143
 */
public class Issue143 {
   public static void main(String[] args) {
      // create a new Projog instance with an Observer that will receive notification of debug events
      CallStackObserver observer = new CallStackObserver();
      CallStack stack = observer.getCallstack();
      Projog p = new Projog(observer);

      // consult file containing rules and facts
      p.consultFile(new File("src/main/resources/Issue143.pl"));

      // call "trace" built-in predicate to enable debugging
      // see: http://www.projog.org/prolog-debugging.html
      p.query("trace.").getResult().next();

      System.out.println("FIRST QUERY");
      QueryStatement s1 = p.query("testRule(X, Y).");
      QueryResult r1 = s1.getResult();
      while (r1.next()) {
         System.out.println("---------------------------------------");
         System.out.println("ANSWER: " + r1.getTerm("X") + " is the brother of " + r1.getTerm("Y"));
         log(stack);
      }

      // NOTE: need to call clear() on stack so it is reset before making the next query
      stack.clear();

      System.out.println("SECOND QUERY");
      QueryStatement s2 = p.query("testRule(roomA, roomC).");
      QueryResult r2 = s2.getResult();
      while (r2.next()) {
         System.out.println("---------------------------------------");
         log(stack);
      }
   }

   private static void log(final CallStack stack) {
      boolean first = true;
      for (CallStack.Element e : stack) {
         if (first) {
            System.out.println("Input: " + e.getInputArgs());
            System.out.println();
            first = false;
         }

         System.out.println("Matched " + e.getPredicateKey() + " clause number " + e.getClauseIdx());
         System.out.println("Clause: " + e.getClause());
         System.out.println("Output: " + e.getOutputArgs());
         System.out.println();
      }
   }
}
