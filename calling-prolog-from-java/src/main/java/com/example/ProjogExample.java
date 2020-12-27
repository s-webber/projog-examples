package com.example;

import java.io.File;

import org.projog.api.Projog;
import org.projog.api.QueryResult;
import org.projog.api.QueryStatement;
import org.projog.core.term.Atom;

public class ProjogExample {
   public static void main(String[] args) {
      // Create a new Projog instance.
      Projog projog = new Projog();

      // Read Prolog facts and rules from a file to populate the "Projog" instance.
      projog.consultFile(new File("src/main/resources/test.pl"));

      // Execute a query and iterate through all the results.
      QueryResult r1 = projog.executeQuery("test(X,Y).");
      while (r1.next()) {
         System.out.println("X = " + r1.getTerm("X") + " Y = " + r1.getTerm("Y"));
      }

      // Execute a query, set a variable and iterate through all the results.
      QueryStatement s1 = projog.createStatement("test(X,Y).");
      s1.setTerm("X", new Atom("d"));
      QueryResult r2 = s1.executeQuery();
      while (r2.next()) {
         System.out.println("Y = " + r2.getTerm("Y"));
      }

      // Execute a query and iterate through all the results.
      QueryResult r3 = projog.executeQuery("testRule(X).");
      while (r3.next()) {
         System.out.println("X = " + r3.getTerm("X"));
      }

      // Execute a query that uses a conjunction. See: http://projog.org/Conjunction.html
      QueryResult r4 = projog.executeQuery("test(X, Y), Y<3.");
      while (r4.next()) {
         System.out.println("X = " + r4.getTerm("X") + " Y = " + r4.getTerm("Y"));
      }
   }
}
