package io.truemark.otel.core.models;

public class OtelLoggingConfigData {
  private final boolean loggingEnabled;
  private final boolean isBatchingEnabled;

  public OtelLoggingConfigData(boolean loggingEnabled, boolean isBatchingEnabled) {
    this.loggingEnabled = loggingEnabled;
    this.isBatchingEnabled = isBatchingEnabled;
  }

  public boolean isLoggingEnabled() {
    return loggingEnabled;
  }

  public boolean isBatchingEnabled() {
    return isBatchingEnabled;
  }

  @Override
  public String toString() {
    return "OtelLoggingConfigData{"
        + "loggingEnabled="
        + loggingEnabled
        + ", isBatchingEnabled="
        + isBatchingEnabled
        + '}';
  }
}
