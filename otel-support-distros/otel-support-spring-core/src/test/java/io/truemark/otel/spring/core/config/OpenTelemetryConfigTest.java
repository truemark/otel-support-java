// OpenTelemetryConfigTest.java
package io.truemark.otel.spring.core.config;

import static io.truemark.otel.spring.core.utils.OtelCustomProperties.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.truemark.otel.core.config.OpenTelemetryStartupConfig;
import io.truemark.otel.core.models.OtelLoggingConfigData;
import io.truemark.otel.core.models.OtelMeterConfigData;
import io.truemark.otel.core.models.OtelOtlpConfigData;
import io.truemark.otel.core.models.OtelServiceConfigData;
import io.truemark.otel.core.models.OtelTracingConfigData;
import io.truemark.otel.spring.core.registries.OtelLoggingExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelMetricExportersRegistry;
import io.truemark.otel.spring.core.registries.OtelMetricViewersRegistry;
import io.truemark.otel.spring.core.registries.OtelTracingSamplerRegistry;
import io.truemark.otel.spring.core.registries.OtelTracingSpanExportersRegistry;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class OpenTelemetryConfigTest {

  @Mock private Environment env;
  @Mock private OtelTracingSpanExportersRegistry tracingSpanExportersRegistry;
  @Mock private OtelTracingSamplerRegistry tracingSamplerRegistry;
  @Mock private OtelMetricExportersRegistry metricExportersRegistry;
  @Mock private OtelMetricViewersRegistry metricViewersRegistry;
  @Mock private OtelLoggingExportersRegistry loggingExportersRegistry;

  private OpenTelemetryConfig openTelemetryConfig;

  @BeforeEach
  void setUp() {
    openTelemetryConfig = new OpenTelemetryConfig(env);
  }

  @AfterEach
  void tearDown() throws Exception {
    resetGlobalOpenTelemetry();
  }

  static Stream<Arguments> configDataProvider() {
    return Stream.of(
        Arguments.of(false, true, true, true),
        Arguments.of(false, true, true, false),
        Arguments.of(false, true, false, true),
        Arguments.of(false, true, false, false),
        Arguments.of(false, false, true, true),
        Arguments.of(false, false, true, false),
        Arguments.of(false, false, false, true),
        Arguments.of(false, false, false, false),
        Arguments.of(false, true, true, true),
        Arguments.of(true, true, true, false),
        Arguments.of(true, true, false, true),
        Arguments.of(true, true, false, false),
        Arguments.of(true, false, true, true),
        Arguments.of(true, false, true, false),
        Arguments.of(true, false, false, true),
        Arguments.of(true, false, false, false));
  }

  static Stream<Boolean> booleanProvider() {
    return Stream.of(true, false);
  }

  @ParameterizedTest
  @MethodSource("configDataProvider")
  void test_openTelemetryStartupConfig_givenVaryingInputs(
      boolean otlpEnabled, boolean tracingEnabled, boolean metricsEnabled, boolean loggingEnabled) {
    String serviceName = "test-service";
    String serviceVersion = "1.0.0";
    final String otlpEndpoint = "http://localhost:4317";
    final OtelOtlpConfigData otlpConfig;
    if (otlpEnabled) {
      otlpConfig = new OtelOtlpConfigData(otlpEnabled, otlpEndpoint);
    } else {
      otlpConfig = new OtelOtlpConfigData(otlpEnabled, null);
    }
    when(env.getRequiredProperty(OTEL_SERVICE_NAME, String.class)).thenReturn(serviceName);
    when(env.getRequiredProperty(OTEL_SERVICE_VERSION, String.class)).thenReturn(serviceVersion);

    OtelTracingConfigData tracingConfigData =
        new OtelTracingConfigData(tracingEnabled, Collections.emptyList(), otlpConfig);
    OtelMeterConfigData meterConfigData =
        new OtelMeterConfigData(metricsEnabled, Collections.emptyList(), otlpConfig);
    OtelLoggingConfigData loggingConfigData =
        new OtelLoggingConfigData(loggingEnabled, Collections.emptyList());

    OpenTelemetryStartupConfig startupConfig =
        openTelemetryConfig.openTelemetryStartupConfig(
            tracingConfigData, meterConfigData, loggingConfigData);

    assertNotNull(startupConfig);
  }

  @ParameterizedTest
  @MethodSource("booleanProvider")
  void test_tracingConfigData_givenVaryingInputs(boolean tracingEnabled) {
    when(env.getProperty(OTEL_TRACES_EXPORTER))
        .thenReturn(tracingEnabled ? EXPORTER_CONSOLE : EXPORTER_NONE);
    when(tracingSpanExportersRegistry.getRegisterSpanExporterHolders())
        .thenReturn(Collections.emptyList());
    when(tracingSamplerRegistry.getRegisteredSampler()).thenReturn(null);

    OtelTracingConfigData tracingConfigData =
        openTelemetryConfig.tracingConfigData(tracingSpanExportersRegistry, tracingSamplerRegistry);

    assertEquals(tracingEnabled, tracingConfigData.isTracingEnabled());
    assertNotNull(tracingConfigData.getTraceSpanExporters());
  }

  @ParameterizedTest
  @MethodSource("booleanProvider")
  void test_metricsConfigData_givenVaryingInputs(boolean metricsEnabled) {
    when(env.getProperty(OTEL_METRICS_EXPORTER))
        .thenReturn(metricsEnabled ? EXPORTER_CONSOLE : EXPORTER_NONE);
    when(metricExportersRegistry.getRegisteredMetricExporters())
        .thenReturn(Collections.emptyList());
    when(metricViewersRegistry.getRegisteredMetricViews()).thenReturn(Collections.emptyList());

    OtelMeterConfigData meterConfigData =
        openTelemetryConfig.metricsConfigData(metricExportersRegistry, metricViewersRegistry);

    assertEquals(metricsEnabled, meterConfigData.isMeterEnabled());
    assertNotNull(meterConfigData.getMetricExporterHolders());
    assertNotNull(meterConfigData.getMetricViewHolders());
  }

  @ParameterizedTest
  @MethodSource("booleanProvider")
  void test_loggingConfigData_givenVaryingInputs(boolean loggingEnabled) {
    when(env.getProperty(OTEL_LOGS_EXPORTER))
        .thenReturn(loggingEnabled ? EXPORTER_CONSOLE : EXPORTER_NONE);
    when(loggingExportersRegistry.getRegisteredLoggingExporters())
        .thenReturn(Collections.emptyList());

    OtelLoggingConfigData loggingConfigData =
        openTelemetryConfig.loggingConfigData(loggingExportersRegistry);

    assertEquals(loggingEnabled, loggingConfigData.isLoggingEnabled());
    assertNotNull(loggingConfigData.getLogRecordExporterHolders());
  }

  @Test
  void test_createServiceConfigData_givenVaryingInputs() {
    String serviceName = "test-service";
    String serviceVersion = "1.0.0";
    when(env.getRequiredProperty(OTEL_SERVICE_NAME, String.class)).thenReturn(serviceName);
    when(env.getRequiredProperty(OTEL_SERVICE_VERSION, String.class)).thenReturn(serviceVersion);

    OtelServiceConfigData serviceConfigData = openTelemetryConfig.createServiceConfigData();

    assertEquals(serviceName, serviceConfigData.getServiceName());
    assertEquals(serviceVersion, serviceConfigData.getServiceVersion());
  }

  private static void resetGlobalOpenTelemetry() throws Exception {
    java.lang.reflect.Field field =
        GlobalOpenTelemetry.class.getDeclaredField("globalOpenTelemetry");
    field.setAccessible(true);
    field.set(null, null);
  }
}
