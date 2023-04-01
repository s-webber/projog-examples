package com.example;

import org.projog.api.Projog;
import org.projog.api.QueryResult;

public class TfilterExample {
   public static void main(String... args) {
      Projog p = new Projog();
      // read tfilter.pl from: src/main/resources
      p.consultResource("tfilter.pl");

      // use query taken from: https://www.scryer.pl/reif.html
      QueryResult r = p.executeQuery("tfilter(=(a), [Variable1,Variable2], Variable3).");
      while (r.next()) {
         System.out.println("Variable1 = " + r.getTerm("Variable1"));
         System.out.println("Variable2 = " + r.getTerm("Variable2"));
         System.out.println("Variable3 = " + p.formatTerm(r.getTerm("Variable3")));
         System.out.println();
      }
   }
}
