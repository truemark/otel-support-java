package io.truemark.otel.spring.core.config;

import io.opentelemetry.api.OpenTelemetry;
import io.truemark.otel.core.config.OpenTelemetryStartupConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenTelemetryConfig {

  @Bean
  public OpenTelemetryStartupConfig openTelemetryStartupConfig(final Environment env) {
    return null;
  }

  @Bean
  public OpenTelemetry openTelemetry(OpenTelemetryStartupConfig openTelemetryStartupConfig) {
    return openTelemetryStartupConfig.getOpenTelemetry();
  }
}
