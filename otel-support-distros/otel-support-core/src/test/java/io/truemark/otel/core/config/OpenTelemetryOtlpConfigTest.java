package io.truemark.otel.core.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.truemark.otel.core.models.*;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpenTelemetryOtlpConfigTest {

  private OpenTelemetrySetupData otelSetupData;
  private OtelServiceConfigData serviceConfig;
  private OtelOtlpConfigData otlpConfig;
  private OtelTracingConfigData tracingConfig;
  private OtelMeterConfigData meterConfig;
  private OtelLoggingConfigData loggingConfig;

  private OpenTelemetryOtlpConfig openTelemetryOtlpConfig;

  @BeforeEach
  public void setUp() {
    serviceConfig = new OtelServiceConfigData("test-service", "1.0.0");
    otlpConfig = new OtelOtlpConfigData(true, "http://localhost:4317");
    tracingConfig = new OtelTracingConfigData(true, true);
    meterConfig = new OtelMeterConfigData(true, Collections.emptyList());
    loggingConfig = new OtelLoggingConfigData(true, true);

    otelSetupData =
        new OpenTelemetrySetupData(
            serviceConfig, otlpConfig, tracingConfig, meterConfig, loggingConfig);
  }

  @AfterEach
  public void tearDown() throws Exception {
    resetGlobalOpenTelemetry();
  }

  @ParameterizedTest
  @CsvSource({
    "true, true, true, true, true",
    "true, true, true, false, false",
    "true, false, false, true, true",
    "false, false, false, false, false"
  })
  public void test_OpenTelemetryOtlpConfigConstructor_givenVaryingConfigInputs(
      boolean otlpEnabled,
      boolean tracingEnabled,
      boolean batchingEnabled,
      boolean meterEnabled,
      boolean loggingEnabled) {
    otlpConfig = new OtelOtlpConfigData(otlpEnabled, otlpEnabled ? "http://localhost:4317" : null);
    tracingConfig = new OtelTracingConfigData(tracingEnabled, batchingEnabled);
    meterConfig = new OtelMeterConfigData(meterEnabled, Collections.emptyList());
    loggingConfig = new OtelLoggingConfigData(loggingEnabled, batchingEnabled);

    otelSetupData =
        new OpenTelemetrySetupData(
            serviceConfig, otlpConfig, tracingConfig, meterConfig, loggingConfig);

    openTelemetryOtlpConfig = new OpenTelemetryOtlpConfig(otelSetupData);

    OpenTelemetry openTelemetry = openTelemetryOtlpConfig.getOpenTelemetry();
    assertNotNull(openTelemetry);
  }

  @Test
  public void testGetOpenTelemetry() {
    openTelemetryOtlpConfig = new OpenTelemetryOtlpConfig(otelSetupData);
    OpenTelemetry openTelemetry = openTelemetryOtlpConfig.getOpenTelemetry();
    assertNotNull(openTelemetry);
  }

  private static void resetGlobalOpenTelemetry() throws Exception {
    java.lang.reflect.Field field =
        GlobalOpenTelemetry.class.getDeclaredField("globalOpenTelemetry");
    field.setAccessible(true);
    field.set(null, null);
  }
}
