package io.truemark.otel.core.models;

import java.util.List;

public class OtelLoggingConfigData {
  private final boolean loggingEnabled;
  private final List<LogRecordExporterHolder> logRecordExporterHolders;

  public OtelLoggingConfigData(
      boolean loggingEnabled, List<LogRecordExporterHolder> logRecordExporterHolders) {
    this.loggingEnabled = loggingEnabled;
    this.logRecordExporterHolders = logRecordExporterHolders;
  }

  public boolean isLoggingEnabled() {
    return loggingEnabled;
  }

  public List<LogRecordExporterHolder> getLogRecordExporterHolders() {
    return logRecordExporterHolders;
  }

  @Override
  public String toString() {
    return "OtelLoggingConfigData{"
        + "loggingEnabled="
        + loggingEnabled
        + ", logRecordExporterHolders="
        + logRecordExporterHolders
        + '}';
  }
}
