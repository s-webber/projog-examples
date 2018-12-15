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
         called(projogEvent);
      } else if (type == ProjogEventType.EXIT) {
         matched((SpyPointEvent) projogEvent.getDetails());
      } else if (type == ProjogEventType.FAIL) {
         failed((SpyPointEvent) projogEvent.getDetails());
      } else if (type == ProjogEventType.REDO) {
         retrying(projogEvent);
      }
   }

   private void called(ProjogEvent projogEvent) {
      SpyPointEvent spyPointEvent = (SpyPointEvent) projogEvent.getDetails();
      Element e = new Element(spyPointEvent, projogEvent.getSource());
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

   private void retrying(ProjogEvent projogEvent) {
      for (Element element : elements) {
         if (element.source == projogEvent.getSource()) {
            element.second = null;
            return;
         }
      }
   }

   @Override
   public Iterator<Element> iterator() {
      return elements.iterator();
   }

   static class Element {
      private final Object source;
      private final SpyPointEvent first;
      private SpyPointEvent second;

      private Element(SpyPointEvent first, Object source) {
         this.first = first;
         this.source = source;
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
