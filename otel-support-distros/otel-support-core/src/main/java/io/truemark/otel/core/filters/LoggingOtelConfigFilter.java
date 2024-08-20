// LoggingOtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.creators.SdkLoggerProviderCreator;
import io.truemark.otel.core.models.LogRecordExporterHolder;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import io.truemark.otel.core.models.OtelLoggingConfigData;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class LoggingOtelConfigFilter implements OtelConfigFilter {

  private static final Logger log = Logger.getLogger(LoggingOtelConfigFilter.class.getName());

  @Override
  public void apply(
      final OpenTelemetrySdkBuilder builder,
      final Resource resource,
      final OpenTelemetrySetupData setupData,
      final OtelConfigFilterChain filterChain) {
    if (setupData.getOtelLoggingConfig().isLoggingEnabled()) {
      SdkLoggerProvider sdkLoggerProvider = createSdkLoggerProvider(resource, setupData);
      builder.setLoggerProvider(sdkLoggerProvider);
    } else {
      log.info("Otel Logging is disabled");
    }
    filterChain.doFilter(builder, resource, setupData);
  }

  // Create SdkLoggerProvider based on the setup data
  private SdkLoggerProvider createSdkLoggerProvider(
      final Resource resource, OpenTelemetrySetupData setupData) {
    final List<LogRecordExporterHolder> logRecordExporterHolders =
        setupData.getOtelLoggingConfig().getLogRecordExporterHolders();
    final OtelLoggingConfigData otelLoggingConfig;
    if (logRecordExporterHolders == null || logRecordExporterHolders.isEmpty()) {
      final LogRecordExporter logRecordExporter = OtlpGrpcLogRecordExporter.builder().build();
      otelLoggingConfig =
          new OtelLoggingConfigData(
              true,
              Collections.singletonList(new LogRecordExporterHolder(true, logRecordExporter)));
    } else {
      otelLoggingConfig = setupData.getOtelLoggingConfig();
    }

    return SdkLoggerProviderCreator.createLoggerProvider(resource, otelLoggingConfig);
  }
}
