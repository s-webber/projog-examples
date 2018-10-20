package com.example;

import org.projog.core.function.math.AbstractUnaryArithmeticOperator;;

/* TEST
 ?- pj_add_arithmetic_operator(triple/1, 'com.example.ArithmeticOperatorExample').

 %TRUE 9 is triple(3)
 %FALSE 9 is triple(4)

 %QUERY X is triple(0)
 %ANSWER X=0

 %QUERY X is triple(1)
 %ANSWER X=3

 %QUERY X is triple(147)
 %ANSWER X=441

 %QUERY X is triple(-42.5)
 %ANSWER X=-127.5
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
