package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.projog.core.event.SpyPoints.SpyPointEvent;
import org.projog.core.event.SpyPoints.SpyPointExitEvent;
import org.projog.core.predicate.PredicateKey;
import org.projog.core.predicate.udp.ClauseModel;

class CallStack implements Iterable<CallStack.Element> {
   private final List<Element> elements = new ArrayList<>();

   void clear() {
      elements.clear();
   }

   void called(SpyPointEvent spyPointEvent) {
      Element e = new Element(spyPointEvent, spyPointEvent.getSourceId());
      for (int i = 0; i < elements.size(); i++) {
         if (elements.get(i).orphaned) {
            removeTrailingElements(i);
            break;
         }
      }
      elements.add(e);
   }

   private void removeTrailingElements(final int startIdx) {
      for (int i = elements.size() - 1; i >= startIdx; i--) {
         elements.remove(i);
      }
   }

   void matched(SpyPointExitEvent spyPointEvent) {
      for (int i = elements.size() - 1; i > -1; i--) {
         Element element = elements.get(i);
         if (isSameSource(spyPointEvent, element)) {
            element.second = spyPointEvent;
            element.orphaned = false;
            unorphanPrecedingElements(i);
            return;
         }
      }
      throw new RuntimeException();
   }

   private void unorphanPrecedingElements(final int subsequentIdx) {
      for (int i = subsequentIdx - 1; i > -1; i--) {
         elements.get(i).orphaned = false;
      }
   }

   void failed(SpyPointEvent spyPointEvent) {
      for (int i = elements.size() - 1; i > -1; i--) {
         Element element = elements.get(i);
         elements.remove(i);
         if (isSameSource(spyPointEvent, element)) {
            return;
         }
      }
      throw new RuntimeException();
   }

   void retrying(SpyPointEvent spyPointEvent) {
      for (int i = elements.size() - 1; i > -1; i--) {
         Element element = elements.get(i);
         if (isSameSource(spyPointEvent, element)) {
            element.second = null;
            element.orphaned = false;
            return;
         } else {
            element.orphaned = true;
         }
      }
      throw new RuntimeException();
   }

   private static boolean isSameSource(SpyPointEvent spyPointEvent, Element element) {
      return element.sourceId == spyPointEvent.getSourceId();
   }

   @Override
   public Iterator<Element> iterator() {
      return elements.iterator();
   }

   static class Element {
      private final int sourceId;
      private final SpyPointEvent first;
      private SpyPointExitEvent second;
      private boolean orphaned;

      private Element(SpyPointEvent first, int sourceId) {
         this.first = first;
         this.sourceId = sourceId;
      }

      PredicateKey getPredicateKey() {
         return first.getPredicateKey();
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
