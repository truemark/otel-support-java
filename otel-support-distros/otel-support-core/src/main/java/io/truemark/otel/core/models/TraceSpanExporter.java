package io.truemark.otel.core.models;

import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.Objects;

public class TraceSpanExporter {

  private final boolean isBatchingEnabled;
  private final SpanExporter spanExporter;

  public TraceSpanExporter(boolean isBatchingEnabled, SpanExporter spanExporter) {
    Objects.requireNonNull(spanExporter, "Span exporter must not be provide");
    this.isBatchingEnabled = isBatchingEnabled;
    this.spanExporter = spanExporter;
  }

  public boolean isBatchingEnabled() {
    return isBatchingEnabled;
  }

  public SpanExporter getSpanExporter() {
    return spanExporter;
  }

  @Override
  public String toString() {
    return "TraceSpanExporter{"
        + "isBatchingEnabled="
        + isBatchingEnabled
        + ", spanExporter="
        + spanExporter
        + '}';
  }
}
