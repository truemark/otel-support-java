// LoggingOtelConfigFilter.java
package io.truemark.otel.core.filters;

import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.creators.SdkLoggerProviderCreator;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
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
      Resource resource, OpenTelemetrySetupData setupData) {
    LogRecordExporter logRecordExporter = OtlpGrpcLogRecordExporter.builder().build();
    return SdkLoggerProviderCreator.createLoggerProvider(
        resource, logRecordExporter, setupData.getOtelLoggingConfig().isBatchingEnabled());
  }
}
