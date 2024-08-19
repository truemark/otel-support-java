package io.truemark.otel.core.models;

import java.util.List;

public class OtelMeterConfigData {
  private final boolean isMeterEnabled;
  private List<MetricView> metricViews;

  public OtelMeterConfigData(boolean isMeterEnabled) {
    this.isMeterEnabled = isMeterEnabled;
  }

  public boolean isMeterEnabled() {
    return isMeterEnabled;
  }

  public List<MetricView> getMetricViews() {
    return metricViews;
  }

    public void setMetricViews(List<MetricView> metricViews) {
        this.metricViews = metricViews;
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
