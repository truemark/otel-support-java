package io.truemark.otel.spring.boot.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class OtelEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    Map<String, Object> defaultProperties = new HashMap<>();
    defaultProperties.put("otel.instrumentation.common.default-enabled", "false"); // Default value

    // Check if the user has overridden this property
    if (!environment.containsProperty("otel.instrumentation.common.default-enabled")) {
      environment
          .getPropertySources()
          .addLast(new MapPropertySource("otelDefaults", defaultProperties));
    }
  }
}
