package io.truemark.otel.core.models;

import java.util.List;

public class OtelMeterConfigData {
  private final boolean meterEnabled;
  private final List<MetricExporterHolder> metricExporterHolders;
  private List<MetricViewHolder> metricViewHolders;

  public OtelMeterConfigData(
      boolean meterEnabled, List<MetricExporterHolder> metricExporterHolders) {
    this.meterEnabled = meterEnabled;
    this.metricExporterHolders = metricExporterHolders;
  }

  public boolean isMeterEnabled() {
    return meterEnabled;
  }

  public List<MetricViewHolder> getMetricViewHolders() {
    return metricViewHolders;
  }

  public void setMetricViews(List<MetricViewHolder> metricViewHolders) {
    this.metricViewHolders = metricViewHolders;
  }

  public List<MetricExporterHolder> getMetricExporterHolders() {
    return metricExporterHolders;
  }

  @Override
  public String toString() {
    return "OtelMeterConfigData{"
        + "meterEnabled="
        + meterEnabled
        + ", metricExporterConfig="
        + metricExporterHolders
        + ", metricViewHolders="
        + metricViewHolders
        + '}';
  }
}
