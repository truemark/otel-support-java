// OpenTelemetryConfig.java
package io.truemark.otel.spring.core.config;

import io.opentelemetry.api.OpenTelemetry;
import io.truemark.otel.core.config.OpenTelemetryStartupConfig;
import io.truemark.otel.core.models.MetricExporterHolder;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import io.truemark.otel.core.models.OtelLoggingConfigData;
import io.truemark.otel.core.models.OtelMeterConfigData;
import io.truemark.otel.core.models.OtelOtlpConfigData;
import io.truemark.otel.core.models.OtelServiceConfigData;
import io.truemark.otel.core.models.OtelTracingConfigData;
import io.truemark.otel.core.models.SpanExporterHolder;
import io.truemark.otel.spring.core.registries.OtelLoggingExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelMetricExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelMetricViewersRegistry;
import io.truemark.otel.spring.core.registries.OtelTracingSamplerRegistry;
import io.truemark.otel.spring.core.registries.OtelTracingSpanExportersRegistry;
import io.truemark.otel.spring.core.utils.OtelCustomProperties;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenTelemetryConfig {

  private static final Logger log = Logger.getLogger(OpenTelemetryConfig.class.getName());
  private final Environment env;

  public OpenTelemetryConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public OpenTelemetryStartupConfig openTelemetryStartupConfig(
      final OtelTracingConfigData tracingConfigData,
      final OtelMeterConfigData metricsConfigData,
      final OtelLoggingConfigData loggingConfigData) {
    log.info("Initializing OTEL Configs");
    final OpenTelemetryStartupConfig telemetryStartupConfig =
        new OpenTelemetryStartupConfig(
            new OpenTelemetrySetupData(
                createServiceConfigData(),
                createOtlpConfigData(),
                tracingConfigData,
                metricsConfigData,
                loggingConfigData));
    log.info("OTEL Configs Initialized");
    return telemetryStartupConfig;
  }

  @Bean
  public OpenTelemetry openTelemetry(OpenTelemetryStartupConfig openTelemetryStartupConfig) {
    return openTelemetryStartupConfig.getOpenTelemetry();
  }

  @Bean
  public OtelTracingConfigData tracingConfigData(
      final OtelTracingSpanExportersRegistry tracingSpanExportersRegistry,
      final OtelTracingSamplerRegistry tracingSamplerRegistry) {
    final boolean tracingEnabled =
        env.getProperty(OtelCustomProperties.OTEL_TRACING_ENABLED, Boolean.class, false);
    final List<SpanExporterHolder> spanExporterHolders =
        tracingSpanExportersRegistry != null
            ? tracingSpanExportersRegistry.getRegisterSpanExporterHolders()
            : Collections.emptyList();
    final OtelTracingConfigData tracingConfig =
        new OtelTracingConfigData(tracingEnabled, spanExporterHolders);
    if (tracingSamplerRegistry != null) {
      tracingConfig.setSampler(tracingSamplerRegistry.getRegisteredSampler());
    }
    return tracingConfig;
  }

  @Bean
  public OtelMeterConfigData metricsConfigData(
      final OtelMetricExportersRegistry metricExportersRegistry,
      final OtelMetricViewersRegistry metricViewersRegistry) {
    final boolean metricsEnabled =
        env.getProperty(OtelCustomProperties.OTEL_METRICS_ENABLED, Boolean.class, false);
    final List<MetricExporterHolder> metricExporterHolders =
        metricExportersRegistry != null
            ? metricExportersRegistry.getRegisteredMetricExporters()
            : Collections.emptyList();
    final OtelMeterConfigData meterConfig =
        new OtelMeterConfigData(metricsEnabled, metricExporterHolders);
    meterConfig.setMetricViews(
        metricViewersRegistry != null
            ? metricViewersRegistry.getRegisteredMetricViews()
            : Collections.emptyList());
    return meterConfig;
  }

  @Bean
  public OtelLoggingConfigData loggingConfigData(
      final OtelLoggingExportersRegistry loggingExportersRegistry) {
    final boolean loggingEnabled =
        env.getProperty(OtelCustomProperties.OTEL_LOGGING_ENABLED, Boolean.class, false);
    return new OtelLoggingConfigData(
        loggingEnabled,
        loggingExportersRegistry != null
            ? loggingExportersRegistry.getRegisteredLoggingExporters()
            : Collections.emptyList());
  }

  public OtelServiceConfigData createServiceConfigData() {
    String serviceName =
        env.getRequiredProperty(OtelCustomProperties.OTEL_SERVICE_NAME, String.class);
    String serviceVersion =
        env.getRequiredProperty(OtelCustomProperties.OTEL_SERVICE_VERSION, String.class);
    return new OtelServiceConfigData(serviceName, serviceVersion);
  }

  public OtelOtlpConfigData createOtlpConfigData() {
    boolean otlpEnabled =
        env.getProperty(OtelCustomProperties.OTEL_OTLP_ENABLED, Boolean.class, false);
    String otlpEndpoint =
        otlpEnabled
            ? env.getRequiredProperty(OtelCustomProperties.OTEL_OTLP_ENDPOINT, String.class)
            : null;
    return new OtelOtlpConfigData(otlpEnabled, otlpEndpoint);
  }
}
