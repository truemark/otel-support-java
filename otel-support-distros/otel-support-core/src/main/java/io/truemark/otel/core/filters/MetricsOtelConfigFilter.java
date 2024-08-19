// MetricsOtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.creators.SdkMeterProviderCreator;
import io.truemark.otel.core.models.MetricExporterHolder;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import io.truemark.otel.core.models.OtelMeterConfigData;
import java.util.Collections;
import java.util.logging.Logger;

public class MetricsOtelConfigFilter implements OtelConfigFilter {

  private static final Logger log = Logger.getLogger(MetricsOtelConfigFilter.class.getName());

  @Override
  public void apply(
      final OpenTelemetrySdkBuilder builder,
      final Resource defaultResource,
      final OpenTelemetrySetupData setupData,
      final OtelConfigFilterChain filterChain) {
    if (setupData.getOtelMeterConfig().isMeterEnabled()) {
      final boolean canUseDefaultExporter =
          setupData.getOtelMeterConfig().getMetricExporterHolders() == null
              || setupData.getOtelMeterConfig().getMetricExporterHolders().isEmpty();
      final OtelMeterConfigData otelMeterConfig;
      if (canUseDefaultExporter) {
        final OtelMeterConfigData existingOtelMetricConfig = setupData.getOtelMeterConfig();
        final MetricExporter metricExporter =
            setupData.getOtlpConfig().isOtlpEnabled()
                ? OtlpGrpcMetricExporter.builder()
                    .setEndpoint(setupData.getOtlpConfig().getOtlpEndpoint())
                    .build()
                : OtlpGrpcMetricExporter.builder().build();
        otelMeterConfig =
            new OtelMeterConfigData(
                existingOtelMetricConfig.isMeterEnabled(),
                Collections.singletonList(new MetricExporterHolder(metricExporter)));
      } else {
        otelMeterConfig = setupData.getOtelMeterConfig();
      }

      SdkMeterProvider sdkMeterProvider =
          SdkMeterProviderCreator.createMeterProvider(defaultResource, otelMeterConfig);
      builder.setMeterProvider(sdkMeterProvider);
    } else {
      log.info("Otel Meter is disabled");
    }
    filterChain.doFilter(builder, defaultResource, setupData);
  }
}
