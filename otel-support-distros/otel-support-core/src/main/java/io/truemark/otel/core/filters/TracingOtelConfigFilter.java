// TracingOtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.creators.SdkTracerProviderCreator;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import io.truemark.otel.core.models.OtelTracingConfigData;
import io.truemark.otel.core.models.SpanExporterHolder;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class TracingOtelConfigFilter implements OtelConfigFilter {

  private static final Logger log = Logger.getLogger(TracingOtelConfigFilter.class.getName());

  @Override
  public void apply(
      final OpenTelemetrySdkBuilder builder,
      final Resource resource,
      final OpenTelemetrySetupData setupData,
      final OtelConfigFilterChain filterChain) {
    if (setupData.getOtelTracingConfig().isTracingEnabled()) {
      SdkTracerProvider sdkTracerProvider = createSdkTracerProvider(resource, setupData);
      builder.setTracerProvider(sdkTracerProvider);
    } else {
      log.info("Otel Tracing is disabled");
    }
    filterChain.doFilter(builder, resource, setupData);
  }

  // Create SdkTracerProvider based on the setup data
  private SdkTracerProvider createSdkTracerProvider(
      final Resource resource, final OpenTelemetrySetupData setupData) {
    final OtelTracingConfigData otelTracingConfig;
    final List<SpanExporterHolder> spanExporterHolders =
        setupData.getOtelTracingConfig().getTraceSpanExporters();
    if (spanExporterHolders == null || spanExporterHolders.isEmpty()) {
      final SpanExporter spanExporter =
          setupData.getOtlpConfig().isOtlpEnabled()
              ? OtlpGrpcSpanExporter.builder()
                  .setEndpoint(setupData.getOtlpConfig().getOtlpEndpoint())
                  .build()
              : OtlpGrpcSpanExporter.builder().build();
      otelTracingConfig =
          new OtelTracingConfigData(
              true, Collections.singletonList(new SpanExporterHolder(true, spanExporter)));
    } else {
      otelTracingConfig = setupData.getOtelTracingConfig();
    }

    return SdkTracerProviderCreator.createTracerProvider(resource, otelTracingConfig);
  }
}
