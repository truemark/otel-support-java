package io.truemark.observability.otel.creators;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public final class SdkTracerProviderCreator {

  private SdkTracerProviderCreator() {
    super();
  }

  public static SdkTracerProvider createTracerProvider(
      final Resource resource, final SpanExporter spanExporter, final boolean isBatchingEnabled) {
    return SdkTracerProvider.builder()
        .addSpanProcessor(
            isBatchingEnabled
                ? BatchSpanProcessor.builder(spanExporter).build()
                : SimpleSpanProcessor.builder(spanExporter).build())
        .setResource(resource)
        .build();
  }
}
