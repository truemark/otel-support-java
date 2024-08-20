package io.truemark.otel.core.models;

import io.opentelemetry.api.internal.StringUtils;

public class OtelServiceConfigData {
  private final String serviceName;
  private final String serviceVersion;

  public OtelServiceConfigData(String serviceName, String serviceVersion) {

    if (StringUtils.isNullOrEmpty(serviceName) || StringUtils.isNullOrEmpty(serviceVersion)) {
      throw new IllegalArgumentException("Service name and version must be provided");
    }

    this.serviceName = serviceName;
    this.serviceVersion = serviceVersion;
  }

  public String getServiceName() {
    return serviceName;
  }

  public String getServiceVersion() {
    return serviceVersion;
  }

  @Override
  public String toString() {
    return "OtelServiceConfigData{"
        + "serviceName='"
        + serviceName
        + '\''
        + ", serviceVertion='"
        + serviceVersion
        + '\''
        + '}';
  }
}
