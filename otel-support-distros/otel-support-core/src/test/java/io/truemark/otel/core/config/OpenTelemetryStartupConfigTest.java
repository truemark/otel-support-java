package io.truemark.otel.core.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.truemark.otel.core.models.LogRecordExporterHolder;
import io.truemark.otel.core.models.OpenTelemetrySetupData;
import io.truemark.otel.core.models.OtelLoggingConfigData;
import io.truemark.otel.core.models.OtelMeterConfigData;
import io.truemark.otel.core.models.OtelOtlpConfigData;
import io.truemark.otel.core.models.OtelServiceConfigData;
import io.truemark.otel.core.models.OtelTracingConfigData;
import io.truemark.otel.core.models.SpanExporterHolder;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenTelemetryStartupConfigTest {

  private OpenTelemetrySetupData otelSetupData;
  private OtelServiceConfigData serviceConfig;
  private OtelOtlpConfigData otlpConfig;
  private OtelTracingConfigData tracingConfig;
  private OtelMeterConfigData meterConfig;
  private OtelLoggingConfigData loggingConfig;

  private OpenTelemetryStartupConfig openTelemetryStartupConfig;

  @BeforeEach
  void setUp() {
    serviceConfig = new OtelServiceConfigData("test-service", "1.0.0");
    otlpConfig = new OtelOtlpConfigData(true, "http://localhost:4317");
    tracingConfig =
        new OtelTracingConfigData(
            true,
            Collections.singletonList(new SpanExporterHolder(true, mock(SpanExporter.class))),
            otlpConfig);
    meterConfig = new OtelMeterConfigData(true, Collections.emptyList(), otlpConfig);
    loggingConfig = new OtelLoggingConfigData(true, Collections.emptyList());

    otelSetupData =
        new OpenTelemetrySetupData(serviceConfig, tracingConfig, meterConfig, loggingConfig);
  }

  @AfterEach
  void tearDown() throws Exception {
    resetGlobalOpenTelemetry();
  }

  @ParameterizedTest
  @CsvSource({
    "true, true, true, true, true",
    "true, true, true, false, false",
    "true, false, false, true, true",
    "false, false, false, false, false"
  })
  void test_OpenTelemetryOtlpConfigConstructor_givenVaryingConfigInputs(
      boolean otlpEnabled,
      boolean tracingEnabled,
      boolean batchingEnabled,
      boolean meterEnabled,
      boolean loggingEnabled) {
    otlpConfig = new OtelOtlpConfigData(otlpEnabled, otlpEnabled ? "http://localhost:4317" : null);
    tracingConfig =
        new OtelTracingConfigData(
            tracingEnabled,
            Collections.singletonList(
                new SpanExporterHolder(batchingEnabled, mock(SpanExporter.class))),
            otlpConfig);
    meterConfig = new OtelMeterConfigData(meterEnabled, Collections.emptyList(), otlpConfig);
    loggingConfig =
        new OtelLoggingConfigData(
            loggingEnabled,
            Collections.singletonList(
                new LogRecordExporterHolder(batchingEnabled, mock(LogRecordExporter.class))));

    otelSetupData =
        new OpenTelemetrySetupData(serviceConfig, tracingConfig, meterConfig, loggingConfig);

    openTelemetryStartupConfig = new OpenTelemetryStartupConfig(otelSetupData);

    OpenTelemetry openTelemetry = openTelemetryStartupConfig.getOpenTelemetry();
    assertNotNull(openTelemetry);
  }

  @Test
  void testGetOpenTelemetry() {
    openTelemetryStartupConfig = new OpenTelemetryStartupConfig(otelSetupData);
    OpenTelemetry openTelemetry = openTelemetryStartupConfig.getOpenTelemetry();
    assertNotNull(openTelemetry);
  }

  private static void resetGlobalOpenTelemetry() throws Exception {
    java.lang.reflect.Field field =
        GlobalOpenTelemetry.class.getDeclaredField("globalOpenTelemetry");
    field.setAccessible(true);
    field.set(null, null);
  }
}
