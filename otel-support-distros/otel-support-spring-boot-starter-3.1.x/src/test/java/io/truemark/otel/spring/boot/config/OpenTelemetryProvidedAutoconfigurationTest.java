package io.truemark.otel.spring.boot.config;

import io.opentelemetry.api.OpenTelemetry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = OpenTelemetryProvidedAutoconfiguration.class)
@TestPropertySource(
    properties = {
      "otel.instrumentation.jdbc.enabled=true",
      "otel.instrumentation.spring-web.enabled=true"
    })
class OpenTelemetryProvidedAutoconfigurationTest {

  @Autowired private OpenTelemetry openTelemetry;

  @Test
  void contextLoads() {
    Assertions.assertThat(openTelemetry).isNotNull();
  }
}
