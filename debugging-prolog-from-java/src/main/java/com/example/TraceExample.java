package com.example;

import java.io.File;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;

public class TraceExample {
   public static void main(String[] args) {
      // create a new Projog instance with an Observer that will receive notification of debug events
      CallStackObserver observer = new CallStackObserver();
      CallStack stack = observer.getCallstack();
      Projog p = new Projog(observer);

      // consult file containing rules and facts
      p.consultFile(new File("src/main/resources/TraceExample.pl"));

      // call "trace" built-in predicate to enable debugging
      // see: http://www.projog.org/prolog-debugging.html
      p.query("trace.").getResult().next();

      System.out.println("FIRST QUERY");
      QueryStatement s1 = p.query("sister(X, Y).");
      QueryResult r1 = s1.getResult();
      while (r1.next()) {
         System.out.println("ANSWER: " + r1.getTerm("Y") + " is the sister of " + r1.getTerm("X"));
         log(stack);
      }

      // NOTE: need to call clear() on stack so it is reset before making the next query
      stack.clear();

      System.out.println("SECOND QUERY");
      QueryStatement s2 = p.query("brother(X, Y).");
      QueryResult r2 = s2.getResult();
      while (r2.next()) {
         System.out.println("ANSWER: " + r2.getTerm("Y") + " is the brother of " + r2.getTerm("X"));
         log(stack);
      }

      // call "notrace" built-in predicate to disable exhaustive debugging
      // call "spy" to enable debugging for specific predicates
      // see: http://www.projog.org/prolog-debugging.html
      p.query("notrace, spy(brother), spy(siblings), spy(parents).").getResult().next();

      // NOTE: need to call clear() on stack so it is reset before making the next query
      stack.clear();

      System.out.println("THIRD QUERY");
      QueryStatement s3 = p.query("brother(X, Y).");
      QueryResult r3 = s3.getResult();
      while (r3.next()) {
         System.out.println("ANSWER: " + r3.getTerm("Y") + " is the brother of " + r3.getTerm("X"));
         log(stack);
      }
   }

   private static void log(final CallStack stack) {
      for (CallStack.Element e : stack) {
         System.out.println("Matched " + e.getPredicateKey() + " clause number " + e.getClauseNumber());
         System.out.println("Clause: " + e.getFormattedClause());
         System.out.println("Input:  " + e.getFormattedInput());
         System.out.println("Output: " + e.getFormattedOutput());
         System.out.println();
      }
   }
}
