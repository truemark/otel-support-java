package io.truemark.otel.support.sample;

import io.truemark.otel.support.sample.controllers.TestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleApplicationTest {

  @Autowired private TestController testController;

  @Test
  void test() {
    Assertions.assertNotNull(testController.test());
  }
}
