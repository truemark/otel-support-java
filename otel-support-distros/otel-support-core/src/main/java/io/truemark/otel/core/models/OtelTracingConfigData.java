package io.truemark.otel.core.models;

public class OtelTracingConfigData {
  private final boolean tracingEnabled;
  private final boolean isBatchingEnabled;

  public OtelTracingConfigData(boolean tracingEnabled, boolean isBatchingEnabled) {
    this.tracingEnabled = tracingEnabled;
    this.isBatchingEnabled = isBatchingEnabled;
  }

  public boolean isTracingEnabled() {
    return tracingEnabled;
  }

  public boolean isBatchingEnabled() {
    return isBatchingEnabled;
  }

  @Override
  public String toString() {
    return "OtelTracingConfigData{"
        + "tracingEnabled="
        + tracingEnabled
        + ", isBatchingEnabled="
        + isBatchingEnabled
        + '}';
  }
}
