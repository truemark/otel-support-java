package io.truemark.otel.spring.boot.config;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.truemark.otel.core.helpers.MetricsRegistrar;
import io.truemark.otel.core.helpers.MetricsRegistrarImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryProvidedAutoconfiguration {

  @ConditionalOnMissingBean(OpenTelemetry.class)
  @Bean
  public OpenTelemetry openTelemetry() {
    return GlobalOpenTelemetry.get();
  }

  @ConditionalOnMissingBean(Meter.class)
  @Bean
  public Meter defaultMeter(OpenTelemetry openTelemetry) {
    return openTelemetry.getMeterProvider().get("default-app");
  }

  @Bean
  public MetricsRegistrar metricsRegistrar(Meter meter) {
    return new MetricsRegistrarImpl(meter);
  }
}
