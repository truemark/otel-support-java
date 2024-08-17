package io.truemark.otel.core.models;

import io.opentelemetry.api.internal.StringUtils;

public class OtelServiceConfigData {
  private final String serviceName;
  private final String serviceVertion;

  public OtelServiceConfigData(String serviceName, String serviceVertion) {

    if (StringUtils.isNullOrEmpty(serviceName) || StringUtils.isNullOrEmpty(serviceVertion)) {
      throw new IllegalArgumentException("Service name and version must be provided");
    }
    this.serviceName = serviceName;
    this.serviceVertion = serviceVertion;
  }

  public String getServiceName() {
    return serviceName;
  }

  public String getServiceVertion() {
    return serviceVertion;
  }
}
