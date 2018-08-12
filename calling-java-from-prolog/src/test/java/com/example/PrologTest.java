package com.example;

import org.junit.Test;
import org.projog.test.ProjogTestExtractor;
import org.projog.test.ProjogTestExtractorConfig;
import org.projog.test.ProjogTestRunner;

public class PrologTest {
   @Test
   public void test() {
      ProjogTestExtractorConfig config = new ProjogTestExtractorConfig();
      ProjogTestExtractor.extractTests(config);
      ProjogTestRunner.runTests(config.getPrologTestsDirectory()).assertSuccess();
   }
}
