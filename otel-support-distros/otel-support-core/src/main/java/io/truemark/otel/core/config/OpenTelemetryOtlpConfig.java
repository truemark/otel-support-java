package io.truemark.otel.core.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.semconv.ServiceAttributes;
import io.truemark.otel.core.creators.SdkLoggerProviderCreator;
import io.truemark.otel.core.creators.SdkMeterProviderCreator;
import io.truemark.otel.core.creators.SdkTracerProviderCreator;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.Objects;
import java.util.logging.Logger;

public class OpenTelemetryOtlpConfig {

  private static final Logger log = Logger.getLogger(OpenTelemetryOtlpConfig.class.getName());
  private static final AttributeKey<String> SERVICE_NAME_KEY = ServiceAttributes.SERVICE_NAME;
  private static final AttributeKey<String> SERVICE_VERSION_KEY = ServiceAttributes.SERVICE_VERSION;

  private final OpenTelemetrySetupData otelSetupData;
  private OpenTelemetry openTelemetry;

  public OpenTelemetryOtlpConfig(final OpenTelemetrySetupData otelSetupData) {
    this.otelSetupData =
        Objects.requireNonNull(otelSetupData, "OpenTelemetrySetupData must be provided");
    this.initialize();
  }

  private void initialize() {
    final OpenTelemetrySdkBuilder openTelemetryBuilder = OpenTelemetrySdk.builder();
    final Resource resource = createResource();

    configureTracing(openTelemetryBuilder, resource);
    configureMetrics(openTelemetryBuilder, resource);
    configureLogging(openTelemetryBuilder, resource);

    openTelemetryBuilder.setPropagators(
        ContextPropagators.create(W3CTraceContextPropagator.getInstance()));
    this.openTelemetry = openTelemetryBuilder.buildAndRegisterGlobal();
  }

  private Resource createResource() {
    return Resource.getDefault().toBuilder()
        .put(SERVICE_NAME_KEY, otelSetupData.getServiceConfig().getServiceName())
        .put(SERVICE_VERSION_KEY, otelSetupData.getServiceConfig().getServiceVertion())
        .build();
  }

  private void configureTracing(OpenTelemetrySdkBuilder openTelemetryBuilder, Resource resource) {
    if (otelSetupData.getOtelTracingConfig().isTracingEnabled()) {
      SpanExporter spanExporter =
          otelSetupData.getOtlpConfig().isOtlpEnabled()
              ? OtlpGrpcSpanExporter.builder()
                  .setEndpoint(otelSetupData.getOtlpConfig().getOtlpEndpoint())
                  .build()
              : OtlpGrpcSpanExporter.builder().build();

      SdkTracerProvider sdkTracerProvider =
          SdkTracerProviderCreator.createTracerProvider(
              resource, spanExporter, otelSetupData.getOtelTracingConfig().isBatchingEnabled());
      openTelemetryBuilder.setTracerProvider(sdkTracerProvider);
    } else {
      log.info("Otel Tracing is disabled");
    }
  }

  private void configureMetrics(OpenTelemetrySdkBuilder openTelemetryBuilder, Resource resource) {

    if (otelSetupData.getOtelMeterConfig().isMeterEnabled()) {
      final MetricExporter metricExporter =
          otelSetupData.getOtlpConfig().isOtlpEnabled()
              ? OtlpGrpcMetricExporter.builder()
                  .setEndpoint(otelSetupData.getOtlpConfig().getOtlpEndpoint())
                  .build()
              : OtlpGrpcMetricExporter.builder().build();

      final SdkMeterProvider sdkMeterProvider =
          SdkMeterProviderCreator.createMeterProvider(resource, metricExporter);
      openTelemetryBuilder.setMeterProvider(sdkMeterProvider);
    } else {
      log.info("Otel Meter is disabled");
    }
  }

  private void configureLogging(OpenTelemetrySdkBuilder openTelemetryBuilder, Resource resource) {
    if (otelSetupData.getOtelLoggingConfig().isLoggingEnabled()) {
      final LogRecordExporter logRecordExporter = OtlpGrpcLogRecordExporter.builder().build();
      final SdkLoggerProvider sdkLoggerProvider =
          SdkLoggerProviderCreator.createLoggerProvider(
              resource,
              logRecordExporter,
              otelSetupData.getOtelLoggingConfig().isBatchingEnabled());
      openTelemetryBuilder.setLoggerProvider(sdkLoggerProvider);
    } else {
      log.info("Otel Logging is disabled");
    }
  }

  public OpenTelemetry getOpenTelemetry() {
    return openTelemetry;
  }
}
