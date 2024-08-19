package io.truemark.otel.core.models;

import io.opentelemetry.sdk.trace.samplers.Sampler;

public class OtelTracingConfigData {
  private final boolean tracingEnabled;
  private final boolean isBatchingEnabled;
  private Sampler sampler;

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

  public Sampler getSampler() {
    return sampler;
  }

  public void setSampler(Sampler sampler) {
    this.sampler = sampler;
  }

  @Override
  public String toString() {
    return "OtelTracingConfigData{"
        + "tracingEnabled="
        + tracingEnabled
        + ", isBatchingEnabled="
        + isBatchingEnabled
        + ", sampler="
        + sampler
        + '}';
  }
}
