package com.example;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjogExampleTest {
   private static final String EXPECTED_OUTPUT = "X = a Y = 1\n"
                                                 + "X = b Y = 2\n"
                                                 + "X = c Y = 3\n"
                                                 + "X = d Y = 4\n"
                                                 + "X = e Y = 5\n"
                                                 + "X = f Y = 6\n"
                                                 + "X = g Y = 7\n"
                                                 + "X = h Y = 8\n"
                                                 + "X = i Y = 9\n"
                                                 + "Y = 4\n"
                                                 + "X = b\n"
                                                 + "X = d\n"
                                                 + "X = f\n"
                                                 + "X = h\n"
                                                 + "X = a Y = 1\n"
                                                 + "X = b Y = 2\n";

   private final ByteArrayOutputStream redirectedOut = new ByteArrayOutputStream();
   private final PrintStream originalOut = System.out;

   @Before
   public void setUpStreams() {
      System.setOut(new PrintStream(redirectedOut));
   }

   @Test
   public void testCompiledMode() {
      ProjogExample.main(new String[0]);
      assertEquals(EXPECTED_OUTPUT, redirectedOut.toString().replace(System.lineSeparator(), "\n"));
   }

   @After
   public void restoreStreams() {
      System.setOut(originalOut);
   }
}
