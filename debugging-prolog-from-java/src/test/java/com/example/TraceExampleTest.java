package com.example;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TraceExampleTest {
   private final ByteArrayOutputStream redirectedOut = new ByteArrayOutputStream();
   private final PrintStream originalOut = System.out;

   @Before
   public void setUpStreams() {
      System.setOut(new PrintStream(redirectedOut));
   }

   @Test
   public void test() throws IOException {
      TraceExample.main(new String[0]);
      assertEquals(getExpectedOutput(), redirectedOut.toString());
   }

   private String getExpectedOutput() throws IOException {
      StringBuilder sb = new StringBuilder();
      for (String line : Files.readAllLines(new File("src/test/resources/expected.txt").toPath())) {
         sb.append(line);
         sb.append(System.lineSeparator());
      }
      return sb.toString();
   }

   @After
   public void restoreStreams() {
      System.setOut(originalOut);
   }
}
