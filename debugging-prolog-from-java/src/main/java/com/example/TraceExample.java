package com.example;

import java.io.File;

import org.projog.api.Projog;
import org.projog.api.QueryResult;

public class TraceExample {
   public static void main(String[] args) {
      // create a new Projog instance with a ProjogListener that will receive notification of debug events
      CallStackListener listener = new CallStackListener();
      CallStack stack = listener.getCallstack();
      Projog p = new Projog(listener);

      // consult file containing rules and facts
      p.consultFile(new File("src/main/resources/TraceExample.pl"));

      // call "trace" built-in predicate to enable debugging
      // see: http://projog.org/prolog-debugging.html
      p.executeOnce("trace.");

      System.out.println("FIRST QUERY");
      QueryResult r1 = p.createStatement("sister(X, Y).").executeQuery();
      while (r1.next()) {
         System.out.println("ANSWER: " + r1.getTerm("Y") + " is the sister of " + r1.getTerm("X"));
         log(stack);
      }

      // NOTE: need to call clear() on stack so it is reset before making the next query
      stack.clear();

      System.out.println("SECOND QUERY");
      QueryResult r2 = p.createStatement("brother(X, Y).").executeQuery();
      while (r2.next()) {
         System.out.println("ANSWER: " + r2.getTerm("Y") + " is the brother of " + r2.getTerm("X"));
         log(stack);
      }

      // call "notrace" built-in predicate to disable exhaustive debugging
      // call "spy" to enable debugging for specific predicates
      // see: http://projog.org/prolog-debugging.html
      p.createStatement("notrace, spy(brother), spy(siblings), spy(parents).").executeQuery().next();

      // NOTE: need to call clear() on stack so it is reset before making the next query
      stack.clear();

      System.out.println("THIRD QUERY");
      QueryResult r3 = p.createStatement("brother(X, Y).").executeQuery();
      while (r3.next()) {
         System.out.println("ANSWER: " + r3.getTerm("Y") + " is the brother of " + r3.getTerm("X"));
         log(stack);
      }
   }

   private static void log(final CallStack stack) {
      for (CallStack.Element e : stack) {
         System.out.println("Matched " + e.getPredicateKey());
         System.out.println("Clause: " + e.getFormattedClause());
         System.out.println("Input:  " + e.getFormattedInput());
         System.out.println("Output: " + e.getFormattedOutput());
         System.out.println();
      }
   }
}
