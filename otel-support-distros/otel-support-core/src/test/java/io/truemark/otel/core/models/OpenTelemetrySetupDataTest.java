package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OpenTelemetrySetupDataTest {

  @ParameterizedTest
  @CsvSource({
    "test-service, 1.0.0, true, http://localhost:4317, true, true, true, true, true, true",
    "test-service, 1.0.0, false, , false, false, false, false, false, false"
  })
  void test_OpenTelemetrySetupDataConstructorAndGetters_givenValidInputValues(
      String serviceName,
      String serviceVersion,
      boolean otlpEnabled,
      String otlpEndpoint,
      boolean tracingEnabled,
      boolean batchingEnabled,
      boolean meterEnabled,
      boolean loggingEnabled,
      boolean loggingBatchingEnabled) {
    OtelServiceConfigData serviceConfig = new OtelServiceConfigData(serviceName, serviceVersion);
    OtelOtlpConfigData otlpConfig = new OtelOtlpConfigData(otlpEnabled, otlpEndpoint);
    OtelTracingConfigData tracingConfig =
        new OtelTracingConfigData(
            tracingEnabled,
            Collections.singletonList(
                new SpanExporterHolder(batchingEnabled, mock(SpanExporter.class))),
            otlpConfig);
    OtelMeterConfigData meterConfig =
        new OtelMeterConfigData(
            meterEnabled,
            Collections.emptyList(),
            new OtelOtlpConfigData(otlpEnabled, otlpEndpoint));
    OtelLoggingConfigData loggingConfig =
        new OtelLoggingConfigData(
            loggingEnabled,
            Collections.singletonList(
                new LogRecordExporterHolder(
                    loggingBatchingEnabled, mock(LogRecordExporter.class))));

    OpenTelemetrySetupData otelSetupData =
        new OpenTelemetrySetupData(serviceConfig, tracingConfig, meterConfig, loggingConfig);

    assertEquals(serviceConfig, otelSetupData.getServiceConfig());
    assertEquals(otlpConfig, otelSetupData.getOtelTracingConfig().getOtlpConfig());
    assertEquals(tracingConfig, otelSetupData.getOtelTracingConfig());
    assertEquals(meterConfig, otelSetupData.getOtelMeterConfig());
    assertEquals(loggingConfig, otelSetupData.getOtelLoggingConfig());
  }

  @Test
  void test_OpenTelemetrySetupDataConstructor_givenNullInputValues() {
    OtelServiceConfigData serviceConfig = new OtelServiceConfigData("test-service", "1.0.0");
    OtelOtlpConfigData otlpConfig = new OtelOtlpConfigData(true, "http://localhost:4317");
    OtelTracingConfigData tracingConfig =
        new OtelTracingConfigData(
            true,
            Collections.singletonList(new SpanExporterHolder(true, mock(SpanExporter.class))),
            otlpConfig);
    OtelMeterConfigData meterConfig =
        new OtelMeterConfigData(true, Collections.emptyList(), otlpConfig);
    OtelLoggingConfigData loggingConfig = new OtelLoggingConfigData(true, Collections.emptyList());

    assertThrows(
        NullPointerException.class,
        () -> new OpenTelemetrySetupData(null, tracingConfig, meterConfig, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () -> new OpenTelemetrySetupData(serviceConfig, null, meterConfig, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () -> new OpenTelemetrySetupData(serviceConfig, tracingConfig, null, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () -> new OpenTelemetrySetupData(serviceConfig, tracingConfig, meterConfig, null));
  }
}
