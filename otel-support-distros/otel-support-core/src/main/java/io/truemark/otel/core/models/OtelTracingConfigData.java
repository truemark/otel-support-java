package io.truemark.otel.core.models;

public class OtelTracingConfigData {

  private final boolean tracingEnabled;
  private final boolean isBatchingEnabled;

  public OtelTracingConfigData(final boolean tracingEnabled, final boolean isBatchingEnabled) {
    this.tracingEnabled = tracingEnabled;
    this.isBatchingEnabled = isBatchingEnabled;
  }

  public boolean isBatchingEnabled() {
    return isBatchingEnabled;
  }

  public boolean isTracingEnabled() {
    return tracingEnabled;
  }
}
