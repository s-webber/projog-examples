package com.example;

import org.projog.api.Projog;
import org.projog.api.QueryResult;

public class TfilterExample {
   public static void main(String[] args) {
      Projog p = new Projog();
      p.consultResource("tfilter.pl");

      // use query from: https://www.scryer.pl/reif.html
      QueryResult r = p.executeQuery("tfilter(=(a), [X,Y], Es).");
      while (r.next()) {
         System.out.println("X  = " + r.getTerm("X"));
         System.out.println("Y  = " + r.getTerm("Y"));
         System.out.println("Es = " + p.formatTerm(r.getTerm("Es")));
         System.out.println();
      }
   }
}
