// SdkLoggerProviderCreator.java
package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.SdkLoggerProviderBuilder;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;
import io.truemark.otel.core.models.OtelLoggingConfigData;

public final class SdkLoggerProviderCreator {

  private SdkLoggerProviderCreator() {
    super();
  }

  public static SdkLoggerProvider createLoggerProvider(
      final Resource defaultResource, final OtelLoggingConfigData loggingConfig) {
    SdkLoggerProviderBuilder loggerProviderBuilder = SdkLoggerProvider.builder();
    addLogRecordProcessor(loggerProviderBuilder, loggingConfig);
    loggerProviderBuilder.addResource(defaultResource);
    return loggerProviderBuilder.build();
  }

  // Add log record processor based on the batching configuration
  private static void addLogRecordProcessor(
      final SdkLoggerProviderBuilder loggerProviderBuilder,
      final OtelLoggingConfigData loggingConfig) {
    if (loggingConfig.getLogRecordExporterHolders() != null
        && !loggingConfig.getLogRecordExporterHolders().isEmpty()) {
      loggingConfig
          .getLogRecordExporterHolders()
          .forEach(
              logRecordExporterHolder -> {
                loggerProviderBuilder.addLogRecordProcessor(
                    logRecordExporterHolder.isBatchingEnabled()
                        ? BatchLogRecordProcessor.builder(
                                logRecordExporterHolder.getLogRecordExporter())
                            .build()
                        : SimpleLogRecordProcessor.create(
                            logRecordExporterHolder.getLogRecordExporter()));
                if (logRecordExporterHolder.getResource() != null) {
                  loggerProviderBuilder.addResource(logRecordExporterHolder.getResource());
                }
              });
    }
  }
}
