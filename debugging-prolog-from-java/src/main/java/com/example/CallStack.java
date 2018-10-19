package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.projog.core.PredicateKey;
import org.projog.core.SpyPoints.SpyPointEvent;
import org.projog.core.event.ProjogEvent;
import org.projog.core.event.ProjogEventType;
import org.projog.core.udp.ClauseModel;

class CallStack implements Iterable<CallStack.Element> {
   private final List<Element> elements = new ArrayList<>();

   void clear() {
      elements.clear();
   }

   void record(ProjogEvent projogEvent) {
      ProjogEventType type = projogEvent.getType();
      if (type == ProjogEventType.CALL) {
         called((SpyPointEvent) projogEvent.getDetails());
      } else if (type == ProjogEventType.EXIT) {
         matched((SpyPointEvent) projogEvent.getDetails());
      } else if (type == ProjogEventType.FAIL) {
         failed((SpyPointEvent) projogEvent.getDetails());
      }
   }

   private void called(SpyPointEvent spe) {
      Element e = new Element(spe);
      elements.add(e);
   }

   private void matched(SpyPointEvent event) {
      for (int i = elements.size() - 1; i >= 0; i--) {
         Element element = elements.get(i);
         if (event.getPredicateKey().equals(element.getPredicateKey())) {
            element.second = event;
            return;
         } else if (element.second == null) {
            elements.remove(i);
         }
      }
   }

   private void failed(SpyPointEvent event) {
      for (int i = elements.size() - 1; i >= 0; i--) {
         Element element = elements.get(i);
         elements.remove(i);
         if (event.getPredicateKey().equals(element.getPredicateKey())) {
            return;
         }
      }
   }

   @Override
   public Iterator<Element> iterator() {
      return elements.iterator();
   }

   static class Element {
      private final SpyPointEvent first;
      private SpyPointEvent second;

      private Element(SpyPointEvent first) {
         this.first = first;
      }

      PredicateKey getPredicateKey() {
         return first.getPredicateKey();
      }

      int getClauseNumber() {
         return second.getClauseNumber();
      }

      String getFormattedClause() {
         return second.getFormattedClause();
      }

      String getFormattedInput() {
         return first.getFormattedTerm();
      }

      String getFormattedOutput() {
         return second.getFormattedTerm();
      }

      ClauseModel getClauseModel() {
         return second.getClauseModel();
      }
   }
}
