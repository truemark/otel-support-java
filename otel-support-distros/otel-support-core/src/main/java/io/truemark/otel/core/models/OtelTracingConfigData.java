package io.truemark.otel.core.models;

import io.opentelemetry.sdk.trace.samplers.Sampler;
import java.util.List;

public class OtelTracingConfigData {
  private final boolean tracingEnabled;
  private final List<TraceSpanExporter> traceSpanExporters;
  private Sampler sampler;

  public OtelTracingConfigData(boolean tracingEnabled, List<TraceSpanExporter> traceSpanExporters) {
    this.tracingEnabled = tracingEnabled;
    this.traceSpanExporters = traceSpanExporters;
  }

  public boolean isTracingEnabled() {
    return tracingEnabled;
  }

  public List<TraceSpanExporter> getTraceSpanExporters() {
    return traceSpanExporters;
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
        + ", traceSpanExporters="
        + traceSpanExporters
        + ", sampler="
        + sampler
        + '}';
  }
}
