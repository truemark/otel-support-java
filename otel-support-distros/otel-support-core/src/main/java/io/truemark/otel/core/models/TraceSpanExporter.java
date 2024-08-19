package io.truemark.otel.core.models;

import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.Objects;

public class TraceSpanExporter {

  private final boolean batchingEnabled;
  private final SpanExporter spanExporter;

  public TraceSpanExporter(boolean isBatchingEnabled, SpanExporter spanExporter) {
    Objects.requireNonNull(spanExporter, "Span exporter must not be provide");
    this.batchingEnabled = isBatchingEnabled;
    this.spanExporter = spanExporter;
  }

  public boolean isBatchingEnabled() {
    return batchingEnabled;
  }

  public SpanExporter getSpanExporter() {
    return spanExporter;
  }

  @Override
  public String toString() {
    return "TraceSpanExporter{"
        + "batchingEnabled="
        + batchingEnabled
        + ", spanExporter="
        + spanExporter
        + '}';
  }
}
