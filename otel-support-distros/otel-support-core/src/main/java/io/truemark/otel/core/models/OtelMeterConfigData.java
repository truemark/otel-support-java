package io.truemark.otel.core.models;

public class OtelMeterConfigData {
  private final boolean isMeterEnabled;

  public OtelMeterConfigData(boolean isMeterEnabled) {
    this.isMeterEnabled = isMeterEnabled;
  }

  public boolean isMeterEnabled() {
    return isMeterEnabled;
  }

  @Override
  public String toString() {
    return "OtelMeterConfigData{" + "isMeterEnabled=" + isMeterEnabled + '}';
  }
}
