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
      final Resource resource,
      final SpanExporter spanExporter,
      final OtelTracingConfigData tracingConfig) {
    final boolean isBatchingEnabled = tracingConfig.isBatchingEnabled();
    final SdkTracerProviderBuilder tracerProviderBuilder =
        SdkTracerProvider.builder()
            .addSpanProcessor(
                isBatchingEnabled
                    ? BatchSpanProcessor.builder(spanExporter).build()
                    : SimpleSpanProcessor.builder(spanExporter).build())
            .setResource(resource);

    if (tracingConfig.getSampler() != null) {
      tracerProviderBuilder.setSampler(tracingConfig.getSampler());
    }
    return tracerProviderBuilder.build();
  }
}
