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
import io.truemark.otel.spring.core.utils.OtelSignalExporter;
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
    final OtelSignalExporter otelSignalExporter =
        OtelSignalExporter.fromSignalExporterConfigNameAndType(
            OtelCustomProperties.OTEL_TRACES_EXPORTER,
            env.getProperty(OtelCustomProperties.OTEL_TRACES_EXPORTER));
    final boolean tracingEnabled = otelSignalExporter.isTracesEnabled();
    final List<SpanExporterHolder> spanExporterHolders =
        tracingSpanExportersRegistry != null
            ? tracingSpanExportersRegistry.getRegisterSpanExporterHolders()
            : Collections.emptyList();
    final OtelTracingConfigData tracingConfig =
        new OtelTracingConfigData(
            tracingEnabled, spanExporterHolders, createOtlpConfigData(otelSignalExporter));
    if (tracingSamplerRegistry != null) {
      tracingConfig.setSampler(tracingSamplerRegistry.getRegisteredSampler());
    }
    return tracingConfig;
  }

  @Bean
  public OtelMeterConfigData metricsConfigData(
      final OtelMetricExportersRegistry metricExportersRegistry,
      final OtelMetricViewersRegistry metricViewersRegistry) {
    final OtelSignalExporter otelSignalExporter =
        OtelSignalExporter.fromSignalExporterConfigNameAndType(
            OtelCustomProperties.OTEL_METRICS_EXPORTER,
            env.getProperty(OtelCustomProperties.OTEL_METRICS_EXPORTER));
    final boolean metricsEnabled = otelSignalExporter.isMetricsEnabled();
    final List<MetricExporterHolder> metricExporterHolders =
        metricExportersRegistry != null
            ? metricExportersRegistry.getRegisteredMetricExporters()
            : Collections.emptyList();
    final OtelMeterConfigData meterConfig =
        new OtelMeterConfigData(
            metricsEnabled, metricExporterHolders, createOtlpConfigData(otelSignalExporter));
    meterConfig.setMetricViews(
        metricViewersRegistry != null
            ? metricViewersRegistry.getRegisteredMetricViews()
            : Collections.emptyList());
    return meterConfig;
  }

  @Bean
  public OtelLoggingConfigData loggingConfigData(
      final OtelLoggingExportersRegistry loggingExportersRegistry) {
    final OtelSignalExporter otelSignalExporter =
        OtelSignalExporter.fromSignalExporterConfigNameAndType(
            OtelCustomProperties.OTEL_LOGS_EXPORTER,
            env.getProperty(OtelCustomProperties.OTEL_LOGS_EXPORTER));
    final boolean loggingEnabled = otelSignalExporter.isLogsEnabled();
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

  private OtelOtlpConfigData createOtlpConfigData(final OtelSignalExporter otelSignalExporter) {
    boolean otlpEnabled = otelSignalExporter.isOTLPEnabled();
    String otlpEndpoint =
        otlpEnabled
            ? env.getRequiredProperty(OtelCustomProperties.OTEL_OTLP_ENDPOINT, String.class)
            : null;
    return new OtelOtlpConfigData(otlpEnabled, otlpEndpoint);
  }
}
