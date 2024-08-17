package io.truemark.otel.core.models;

import java.util.List;

public class OtelMeterConfigData {
  private final boolean isMeterEnabled;
  private final List<MetricView> metricViews;

  public OtelMeterConfigData(boolean isMeterEnabled, List<MetricView> metricViews) {
    this.isMeterEnabled = isMeterEnabled;
    this.metricViews = metricViews;
  }

  public boolean isMeterEnabled() {
    return isMeterEnabled;
  }

  public List<MetricView> getMetricViews() {
    return metricViews;
  }

  @Override
  public String toString() {
    return "OtelMeterConfigData{"
        + "isMeterEnabled="
        + isMeterEnabled
        + ", metricViews="
        + metricViews
        + '}';
  }
}
