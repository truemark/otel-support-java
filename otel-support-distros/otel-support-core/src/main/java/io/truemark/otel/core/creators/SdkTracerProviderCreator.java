// SdkTracerProviderCreator.java
package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.truemark.otel.core.models.OtelTracingConfigData;

public final class SdkTracerProviderCreator {

  private SdkTracerProviderCreator() {
    super();
  }

  public static SdkTracerProvider createTracerProvider(
      final Resource defaultResource, final OtelTracingConfigData tracingConfig) {
    final SdkTracerProviderBuilder tracerProviderBuilder = SdkTracerProvider.builder();

    setSampler(tracerProviderBuilder, tracingConfig);
    addSpanProcessors(tracerProviderBuilder, tracingConfig);

    if (defaultResource != null) {
      tracerProviderBuilder.addResource(defaultResource);
    }

    return tracerProviderBuilder.build();
  }

  // Set the sampler if it is provided in the tracing configuration
  private static void setSampler(
      SdkTracerProviderBuilder tracerProviderBuilder, OtelTracingConfigData tracingConfig) {
    if (tracingConfig.getSampler() != null) {
      tracerProviderBuilder.setSampler(tracingConfig.getSampler());
    }
  }

  // Add span processors based on the tracing configuration
  private static void addSpanProcessors(
      SdkTracerProviderBuilder tracerProviderBuilder, OtelTracingConfigData tracingConfig) {
    tracingConfig
        .getTraceSpanExporters()
        .forEach(
            traceSpanExporter -> {
              tracerProviderBuilder.addSpanProcessor(
                  traceSpanExporter.isBatchingEnabled()
                      ? BatchSpanProcessor.builder(traceSpanExporter.getSpanExporter()).build()
                      : SimpleSpanProcessor.builder(traceSpanExporter.getSpanExporter()).build());
            });
  }
}
