package com.example;

import org.projog.core.event.ProjogListener;
import org.projog.core.event.SpyPoints.SpyPointEvent;
import org.projog.core.event.SpyPoints.SpyPointExitEvent;

class CallStackListener implements ProjogListener {
   private final ThreadLocal<CallStack> callStacks = new ThreadLocal<CallStack>() {
      @Override
      protected CallStack initialValue() {
         return new CallStack();
      }
   };

   CallStack getCallstack() {
      return callStacks.get();
   }

   @Override
   public void onCall(SpyPointEvent event) {
      getCallstack().called(event);
   }

   @Override
   public void onRedo(SpyPointEvent event) {
      getCallstack().retrying(event);
   }

   @Override
   public void onExit(SpyPointExitEvent event) {
      getCallstack().matched(event);
   }

   @Override
   public void onFail(SpyPointEvent event) {
      getCallstack().failed(event);
   }

   @Override
   public void onWarn(String message) {
   }

   @Override
   public void onInfo(String message) {
   }
}
