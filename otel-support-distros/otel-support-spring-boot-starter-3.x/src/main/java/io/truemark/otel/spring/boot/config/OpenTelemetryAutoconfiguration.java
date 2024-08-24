package io.truemark.otel.spring.boot.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.truemark.otel.core.helpers.MetricsRegistrar;
import io.truemark.otel.core.helpers.MetricsRegistrarImpl;
import io.truemark.otel.spring.core.annotations.EnableOpenTelemetry;
import io.truemark.otel.spring.core.registries.OtelLoggingExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelLoggingExportersRegistryDefaultImpl;
import io.truemark.otel.spring.core.registries.OtelMetricExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelMetricExportersRegistryDefaultImpl;
import io.truemark.otel.spring.core.registries.OtelMetricViewersRegistry;
import io.truemark.otel.spring.core.registries.OtelMetricViewersRegistryDefaultImpl;
import io.truemark.otel.spring.core.registries.OtelTracingSamplerRegistry;
import io.truemark.otel.spring.core.registries.OtelTracingSamplerRegistryDefaultImpl;
import io.truemark.otel.spring.core.registries.OtelTracingSpanExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelTracingSpanExportersRegistryDefaultImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableOpenTelemetry
@Configuration
public class OpenTelemetryAutoconfiguration {

  @ConditionalOnMissingBean(OtelLoggingExportersRegistry.class)
  @Bean
  public OtelLoggingExportersRegistry loggingExportersRegistryDefault() {
    return new OtelLoggingExportersRegistryDefaultImpl();
  }

  @ConditionalOnMissingBean(OtelMetricExportersRegistry.class)
  @Bean
  public OtelMetricExportersRegistry otelMetricExportersRegistryDefault() {
    return new OtelMetricExportersRegistryDefaultImpl();
  }

  @ConditionalOnMissingBean(OtelMetricViewersRegistry.class)
  @Bean
  public OtelMetricViewersRegistry otelMetricViewersRegistry() {
    return new OtelMetricViewersRegistryDefaultImpl();
  }

  @ConditionalOnMissingBean(OtelTracingSamplerRegistry.class)
  @Bean
  public OtelTracingSamplerRegistry otelTracingSamplerRegistry() {
    return new OtelTracingSamplerRegistryDefaultImpl();
  }

  @ConditionalOnMissingBean(OtelTracingSpanExportersRegistry.class)
  @Bean
  public OtelTracingSpanExportersRegistry otelTracingSpanExportersRegistry() {
    return new OtelTracingSpanExportersRegistryDefaultImpl();
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
