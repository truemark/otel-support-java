package io.truemark.otel.core.models;

import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.util.List;

public class OtelTracingConfigData {
  private final boolean tracingEnabled;
  private final List<SpanExporterHolder> spanExporterHolders;
  private final OtelOtlpConfigData otlpConfig;
  private Sampler sampler;

  public OtelTracingConfigData(
      boolean tracingEnabled,
      List<SpanExporterHolder> spanExporterHolders,
      OtelOtlpConfigData otlpConfig) {
    this.tracingEnabled = tracingEnabled;
    this.spanExporterHolders = spanExporterHolders;
    this.otlpConfig = otlpConfig;
  }

  public boolean isTracingEnabled() {
    return tracingEnabled;
  }

  public List<SpanExporterHolder> getTraceSpanExporters() {
    return spanExporterHolders;
  }

  public List<SpanExporterHolder> getSpanExporterHolders() {
    return spanExporterHolders;
  }

  public OtelOtlpConfigData getOtlpConfig() {
    return otlpConfig;
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
        + ", spanExporterHolders="
        + spanExporterHolders
        + ", otlpConfig="
        + otlpConfig
        + ", sampler="
        + sampler
        + '}';
  }
}
