// MetricsOtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.creators.SdkMeterProviderCreator;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import java.util.logging.Logger;

public class MetricsOtelConfigFilter implements OtelConfigFilter {

  private static final Logger log = Logger.getLogger(MetricsOtelConfigFilter.class.getName());

  @Override
  public void apply(
      OpenTelemetrySdkBuilder builder, Resource resource, OpenTelemetrySetupData setupData) {
    if (setupData.getOtelMeterConfig().isMeterEnabled()) {
      MetricExporter metricExporter =
          setupData.getOtlpConfig().isOtlpEnabled()
              ? OtlpGrpcMetricExporter.builder()
                  .setEndpoint(setupData.getOtlpConfig().getOtlpEndpoint())
                  .build()
              : OtlpGrpcMetricExporter.builder().build();

      SdkMeterProvider sdkMeterProvider =
          SdkMeterProviderCreator.createMeterProvider(
              resource, metricExporter, setupData.getOtelMeterConfig().getMetricViews());
      builder.setMeterProvider(sdkMeterProvider);
    } else {
      log.info("Otel Meter is disabled");
    }
  }
}
