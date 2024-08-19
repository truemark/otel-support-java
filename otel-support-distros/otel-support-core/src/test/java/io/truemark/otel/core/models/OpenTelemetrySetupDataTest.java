package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.trace.export.SpanExporter;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OpenTelemetrySetupDataTest {

  @ParameterizedTest
  @CsvSource({
    "test-service, 1.0.0, true, http://localhost:4317, true, true, true, true, true, true",
    "test-service, 1.0.0, false, , false, false, false, false, false, false"
  })
  public void test_OpenTelemetrySetupDataConstructorAndGetters_givenValidInputValues(
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
                new TraceSpanExporter(batchingEnabled, mock(SpanExporter.class))));
    OtelMeterConfigData meterConfig = new OtelMeterConfigData(meterEnabled);
    OtelLoggingConfigData loggingConfig =
        new OtelLoggingConfigData(loggingEnabled, loggingBatchingEnabled);

    OpenTelemetrySetupData otelSetupData =
        new OpenTelemetrySetupData(
            serviceConfig, otlpConfig, tracingConfig, meterConfig, loggingConfig);

    assertEquals(serviceConfig, otelSetupData.getServiceConfig());
    assertEquals(otlpConfig, otelSetupData.getOtlpConfig());
    assertEquals(tracingConfig, otelSetupData.getOtelTracingConfig());
    assertEquals(meterConfig, otelSetupData.getOtelMeterConfig());
    assertEquals(loggingConfig, otelSetupData.getOtelLoggingConfig());
  }

  @Test
  public void test_OpenTelemetrySetupDataConstructor_givenNullInputValues() {
    OtelServiceConfigData serviceConfig = new OtelServiceConfigData("test-service", "1.0.0");
    OtelOtlpConfigData otlpConfig = new OtelOtlpConfigData(true, "http://localhost:4317");
    OtelTracingConfigData tracingConfig =
        new OtelTracingConfigData(
            true, Collections.singletonList(new TraceSpanExporter(true, mock(SpanExporter.class))));
    OtelMeterConfigData meterConfig = new OtelMeterConfigData(true);
    OtelLoggingConfigData loggingConfig = new OtelLoggingConfigData(true, true);

    assertThrows(
        NullPointerException.class,
        () ->
            new OpenTelemetrySetupData(
                null, otlpConfig, tracingConfig, meterConfig, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () ->
            new OpenTelemetrySetupData(
                serviceConfig, null, tracingConfig, meterConfig, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () ->
            new OpenTelemetrySetupData(
                serviceConfig, otlpConfig, null, meterConfig, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () ->
            new OpenTelemetrySetupData(
                serviceConfig, otlpConfig, tracingConfig, null, loggingConfig));
    assertThrows(
        NullPointerException.class,
        () ->
            new OpenTelemetrySetupData(
                serviceConfig, otlpConfig, tracingConfig, meterConfig, null));
  }
}
