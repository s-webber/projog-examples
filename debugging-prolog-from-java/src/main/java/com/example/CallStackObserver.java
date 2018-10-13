package com.example;

import java.util.Observable;
import java.util.Observer;

import org.projog.core.event.ProjogEvent;

class CallStackObserver implements Observer {
   private final ThreadLocal<CallStack> callStacks = new ThreadLocal<CallStack>() {
      @Override
      protected CallStack initialValue() {
         return new CallStack();
      }
   };

   @Override
   public void update(Observable o, Object arg) {
      ProjogEvent pe = (ProjogEvent) arg;
      getCallstack().record(pe);
   }

   CallStack getCallstack() {
      return callStacks.get();
   }
}
