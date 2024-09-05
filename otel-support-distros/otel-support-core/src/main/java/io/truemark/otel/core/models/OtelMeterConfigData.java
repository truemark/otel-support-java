package io.truemark.otel.core.models;

import java.util.List;
import java.util.Objects;

public class OtelMeterConfigData {
  private final boolean meterEnabled;
  private final List<MetricExporterHolder> metricExporterHolders;
  private final OtelOtlpConfigData otlpConfig;
  private List<MetricViewHolder> metricViewHolders;

  public OtelMeterConfigData(
      boolean meterEnabled,
      List<MetricExporterHolder> metricExporterHolders,
      OtelOtlpConfigData otlpConfig) {
    Objects.requireNonNull(otlpConfig, "OTLP config must be provided");
    this.otlpConfig = otlpConfig;
    this.meterEnabled = meterEnabled;
    this.metricExporterHolders = metricExporterHolders;
  }

  public boolean isMeterEnabled() {
    return meterEnabled;
  }

  public OtelOtlpConfigData getOtlpConfig() {
    return otlpConfig;
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
        + ", metricExporterHolders="
        + metricExporterHolders
        + ", otlpConfig="
        + otlpConfig
        + ", metricViewHolders="
        + metricViewHolders
        + '}';
  }
}
