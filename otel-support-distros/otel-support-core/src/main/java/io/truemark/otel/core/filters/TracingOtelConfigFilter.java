// TracingOtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.creators.SdkTracerProviderCreator;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.logging.Logger;

public class TracingOtelConfigFilter implements OtelConfigFilter {

  private static final Logger log = Logger.getLogger(TracingOtelConfigFilter.class.getName());

  @Override
  public void apply(
      OpenTelemetrySdkBuilder builder, Resource resource, OpenTelemetrySetupData setupData) {
    if (setupData.getOtelTracingConfig().isTracingEnabled()) {
      SpanExporter spanExporter =
          setupData.getOtlpConfig().isOtlpEnabled()
              ? OtlpGrpcSpanExporter.builder()
                  .setEndpoint(setupData.getOtlpConfig().getOtlpEndpoint())
                  .build()
              : OtlpGrpcSpanExporter.builder().build();

      SdkTracerProvider sdkTracerProvider =
          SdkTracerProviderCreator.createTracerProvider(
              resource, spanExporter, setupData.getOtelTracingConfig().isBatchingEnabled());
      builder.setTracerProvider(sdkTracerProvider);
    } else {
      log.info("Otel Tracing is disabled");
    }
  }
}
