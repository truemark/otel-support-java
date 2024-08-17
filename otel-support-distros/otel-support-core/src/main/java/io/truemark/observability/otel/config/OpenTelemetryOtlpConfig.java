package io.truemark.observability.otel.config;

import io.opentelemetry.api.OpenTelemetry;
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
import io.truemark.observability.otel.creators.SdkLoggerProviderCreator;
import io.truemark.observability.otel.creators.SdkMeterProviderCreator;
import io.truemark.observability.otel.creators.SdkTracerProviderCreator;

public class OpenTelemetryOtlpConfig {

  private final String serviceName;
  private final String serviceVersion;
  private final boolean isBatchingEnabled;
  private final boolean isConsoleLoggingEnabled;
  private final String collectorUrl;

  private OpenTelemetry openTelemetry;

  public OpenTelemetryOtlpConfig(
      String serviceName,
      String serviceVersion,
      boolean isBatchingEnabled,
      boolean isConsoleLoggingEnabled,
      String collectorUrl) {
    this.serviceName = serviceName;
    this.serviceVersion = serviceVersion;
    this.isBatchingEnabled = isBatchingEnabled;
    this.isConsoleLoggingEnabled = isConsoleLoggingEnabled;
    this.collectorUrl = collectorUrl;
    this.initialize();
  }

  private void initialize() {
    final OpenTelemetrySdkBuilder openTelemetryBuilder = OpenTelemetrySdk.builder();

    final Resource resource =
        Resource.getDefault().toBuilder()
            .put(ServiceAttributes.SERVICE_NAME, serviceName)
            .put(ServiceAttributes.SERVICE_VERSION, serviceVersion)
            .build();

    // Trace Configurations
    final SpanExporter spanExporter =
        OtlpGrpcSpanExporter.builder().setEndpoint(collectorUrl).build();
    final SdkTracerProvider sdkTracerProvider =
        SdkTracerProviderCreator.createTracerProvider(resource, spanExporter, isBatchingEnabled);
    openTelemetryBuilder.setTracerProvider(sdkTracerProvider);

    // Metric Configurations
    final MetricExporter metricExporter =
        OtlpGrpcMetricExporter.builder().setEndpoint(collectorUrl).build();
    final SdkMeterProvider sdkMeterProvider =
        SdkMeterProviderCreator.createMeterProvider(resource, metricExporter);
    openTelemetryBuilder.setMeterProvider(sdkMeterProvider);

    // Logger Configurations
    if (isConsoleLoggingEnabled) {
      final LogRecordExporter logRecordExporter = OtlpGrpcLogRecordExporter.builder().build();
      final SdkLoggerProvider sdkLoggerProvider =
          SdkLoggerProviderCreator.createLoggerProvider(
              resource, logRecordExporter, isBatchingEnabled);
      openTelemetryBuilder.setLoggerProvider(sdkLoggerProvider);
    }

    openTelemetryBuilder.setPropagators(
        ContextPropagators.create(W3CTraceContextPropagator.getInstance()));

    this.openTelemetry = openTelemetryBuilder.buildAndRegisterGlobal();
  }
}
