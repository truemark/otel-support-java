package io.truemark.otel.core.creators;

import io.opentelemetry.sdk.logs.SdkLoggerProvider;
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
    return SdkLoggerProvider.builder()
        .addLogRecordProcessor(
            isBatchingEnabled
                ? BatchLogRecordProcessor.builder(logRecordExporter).build()
                : SimpleLogRecordProcessor.create(logRecordExporter))
        .setResource(resource)
        .build();
  }
}
