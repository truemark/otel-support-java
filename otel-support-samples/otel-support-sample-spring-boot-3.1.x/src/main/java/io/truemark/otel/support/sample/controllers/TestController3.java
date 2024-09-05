package io.truemark.otel.support.sample.controllers;

import io.opentelemetry.api.common.Attributes;
import io.truemark.otel.core.helpers.MetricsRegistrar;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController3 {

  private final MetricsRegistrar metricsRegistrar;

  public TestController3(MetricsRegistrar metricsRegistrar) {
    this.metricsRegistrar = metricsRegistrar;
    this.metricsRegistrar.registerCounter(
        "processed_requests", "Total number of processed requests", "1");
    this.metricsRegistrar.registerCounter("successful_requests", "Successful requests", "1");
    this.metricsRegistrar.registerCounter("failed_requests", "Failed requests", "1");
  }

  @GetMapping("/test")
  public String test() {
    this.metricsRegistrar.incrementCounter("processed_requests", 1, Attributes.empty());
    if (RandomUtils.nextBoolean()) {
      this.metricsRegistrar.incrementCounter("successful_requests", 1, Attributes.empty());
      return "It Works!";
    } else {
      this.metricsRegistrar.incrementCounter("failed_requests", 1, Attributes.empty());
      return "It doesn't work!";
    }
  }
}
