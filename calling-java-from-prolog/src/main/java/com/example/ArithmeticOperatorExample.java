package com.example;

import org.projog.core.math.AbstractUnaryArithmeticOperator;

/* TEST
 ?- pj_add_arithmetic_operator(triple/1, 'com.example.ArithmeticOperatorExample').

 %TRUE 9 is triple(3)
 %FAIL 9 is triple(4)

 %?- X is triple(0)
 % X=0

 %?- X is triple(1)
 % X=3

 %?- X is triple(147)
 % X=441

 %?- X is triple(-42.5)
 % X=-127.5
 */
/**
 * <code>triple</code> - multiples the given number by 3.
 */
public class ArithmeticOperatorExample extends AbstractUnaryArithmeticOperator {

   @Override
   protected double calculateDouble(double input) {
      return input * 3;
   }

   @Override
   protected long calculateLong(long input) {
      return input * 3;
   }
}
