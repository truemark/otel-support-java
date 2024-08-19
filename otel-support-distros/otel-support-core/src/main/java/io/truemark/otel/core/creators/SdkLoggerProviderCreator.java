// SdkLoggerProviderCreator.java
package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.SdkLoggerProviderBuilder;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;

public final class SdkLoggerProviderCreator {

  private SdkLoggerProviderCreator() {
    super();
  }

  public static SdkLoggerProvider createLoggerProvider(
      final Resource resource,
      final LogRecordExporter logRecordExporter,
      final boolean isBatchingEnabled) {
    SdkLoggerProviderBuilder loggerProviderBuilder = SdkLoggerProvider.builder();
    addLogRecordProcessor(loggerProviderBuilder, logRecordExporter, isBatchingEnabled);
    loggerProviderBuilder.setResource(resource);
    return loggerProviderBuilder.build();
  }

  // Add log record processor based on the batching configuration
  private static void addLogRecordProcessor(
      SdkLoggerProviderBuilder loggerProviderBuilder,
      LogRecordExporter logRecordExporter,
      boolean isBatchingEnabled) {
    loggerProviderBuilder.addLogRecordProcessor(
        isBatchingEnabled
            ? BatchLogRecordProcessor.builder(logRecordExporter).build()
            : SimpleLogRecordProcessor.create(logRecordExporter));
  }
}
