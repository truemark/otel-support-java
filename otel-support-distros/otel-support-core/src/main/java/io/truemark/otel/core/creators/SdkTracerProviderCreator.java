package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.models.OtelTracingConfigData;

public final class SdkTracerProviderCreator {

  private SdkTracerProviderCreator() {
    super();
  }

  public static SdkTracerProvider createTracerProvider(
      final Resource defaultResource,
      final SpanExporter spanExporter,
      final OtelTracingConfigData tracingConfig) {
    final SdkTracerProviderBuilder tracerProviderBuilder = SdkTracerProvider.builder();
    if (tracingConfig.getSampler() != null) {
      tracerProviderBuilder.setSampler(tracingConfig.getSampler());
    }

    tracingConfig
        .getTraceSpanExporters()
        .forEach(
            traceSpanExporter -> {
              tracerProviderBuilder.addSpanProcessor(
                  traceSpanExporter.isBatchingEnabled()
                      ? BatchSpanProcessor.builder(spanExporter).build()
                      : SimpleSpanProcessor.builder(spanExporter).build());
            });

    if (defaultResource != null) {
      tracerProviderBuilder.addResource(defaultResource);
    }
    return tracerProviderBuilder.build();
  }
}
