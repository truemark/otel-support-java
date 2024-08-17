package io.truemark.otel.core.models;

public class OtelLoggingConfigData {
  private final boolean loggingEnabled;
  private final boolean isBatchingEnabled;

  public OtelLoggingConfigData(final boolean loggingEnabled, final boolean isBatchingEnabled) {
    this.loggingEnabled = loggingEnabled;
    this.isBatchingEnabled = isBatchingEnabled;
  }

  public boolean isBatchingEnabled() {
    return isBatchingEnabled;
  }

  public boolean isLoggingEnabled() {
    return loggingEnabled;
  }
}
