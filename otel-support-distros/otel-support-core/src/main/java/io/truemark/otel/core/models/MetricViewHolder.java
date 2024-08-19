package io.truemark.otel.core.models;

import io.opentelemetry.sdk.metrics.InstrumentSelector;
import io.opentelemetry.sdk.metrics.View;
import java.util.Objects;

public class MetricViewHolder {

  private final InstrumentSelector selector;
  private final View view;

  public MetricViewHolder(InstrumentSelector selector, View view) {
    Objects.requireNonNull(selector, "selector is required");
    Objects.requireNonNull(view, "view is required");
    this.selector = selector;
    this.view = view;
  }

  public InstrumentSelector getSelector() {
    return selector;
  }

  public View getView() {
    return view;
  }
}
