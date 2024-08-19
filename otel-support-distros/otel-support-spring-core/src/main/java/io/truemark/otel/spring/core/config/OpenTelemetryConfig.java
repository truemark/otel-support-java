package io.truemark.otel.spring.core.config;

import io.opentelemetry.api.OpenTelemetry;
import io.truemark.otel.core.config.OpenTelemetryStartupConfig;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import io.truemark.otel.core.models.OtelOtlpConfigData;
import io.truemark.otel.core.models.OtelServiceConfigData;
import io.truemark.otel.spring.core.utils.OtelCustomProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenTelemetryConfig {

  @Bean
  public OpenTelemetryStartupConfig openTelemetryStartupConfig(final Environment env) {

    final String serviceName =
        env.getRequiredProperty(OtelCustomProperties.OTEL_SERVICE_NAME, String.class);
    final String serviceVersion =
        env.getRequiredProperty(OtelCustomProperties.OTEL_SERVICE_VERSION, String.class);
    final OtelServiceConfigData serviceConfigData =
        new OtelServiceConfigData(serviceName, serviceVersion);

    final boolean otlpEnabled =
        env.getProperty(OtelCustomProperties.OTEL_OTLP_ENABLED, Boolean.class, false);
    final OtelOtlpConfigData otlpConfigData;
    if (otlpEnabled) {
      otlpConfigData =
          new OtelOtlpConfigData(
              otlpEnabled,
              env.getRequiredProperty(OtelCustomProperties.OTEL_OTLP_ENDPOINT, String.class));
    } else {
      otlpConfigData = new OtelOtlpConfigData(otlpEnabled, null);
    }

    final OpenTelemetrySetupData telemetrySetupData =
        new OpenTelemetrySetupData(serviceConfigData, otlpConfigData, null, null, null);
    return new OpenTelemetryStartupConfig(telemetrySetupData);
  }

  @Bean
  public OpenTelemetry openTelemetry(OpenTelemetryStartupConfig openTelemetryStartupConfig) {
    return openTelemetryStartupConfig.getOpenTelemetry();
  }
}
