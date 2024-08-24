// MetricsRegistrarImplTest.java
package io.truemark.otel.core.helpers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.LongUpDownCounter;
import io.opentelemetry.api.metrics.Meter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MetricsRegistrarImplTest {

  private Meter meter;

  private MetricsRegistrarImpl metricRegistrar;

  @BeforeEach
  void setupMeter(TestInfo test) {
    String instrumentationName = "test-" + Thread.currentThread();
    meter =
        GlobalOpenTelemetry.get()
            .getMeterProvider()
            .meterBuilder(instrumentationName)
            .setInstrumentationVersion("1.2.3")
            .setSchemaUrl("http://schema.org")
            .build();
    metricRegistrar = new MetricsRegistrarImpl(meter);
  }

  static Stream<Arguments> listProviderOfDoubleValues() {
    return Stream.of(
        Arguments.of(Collections.emptyList()), Arguments.of(Arrays.asList(1.0, 2.0, 3.0)));
  }

  static Stream<Arguments> listProviderOfLongValues() {
    return Stream.of(
        Arguments.of(Collections.emptyList()), Arguments.of(Arrays.asList(1L, 2L, 3L)));
  }

  static Stream<Arguments> booleanProvider() {
    return Stream.of(Arguments.of(true), Arguments.of(false));
  }

  @Test
  void test_registerCounter_givenCounterName_shouldRegisterCounter() {
    LongCounter counter = metricRegistrar.registerCounter("testCounter", "description", "unit");
    assertNotNull(counter);
  }

  @Test
  void test_registerUpDownCounter_givenUpDownCounterName_shouldRegisterUpDownCounter() {
    LongUpDownCounter upDownCounter =
        metricRegistrar.registerUpDownCounter("testUpDownCounter", "description", "unit");
    assertNotNull(upDownCounter);
  }

  @ParameterizedTest
  @MethodSource("listProviderOfDoubleValues")
  void test_registerDoubleHistogram_givenDoubleHistogramName_shouldRegisterDoubleHistogram(
      List<Double> bucketBoundaries) {
    DoubleHistogram histogram =
        metricRegistrar.registerDoubleHistogram(
            "testHistogram", "description", "unit", bucketBoundaries);
    assertNotNull(histogram);
  }

  @ParameterizedTest
  @MethodSource("listProviderOfLongValues")
  void test_registerLongHistogram_givenLongHistogramName_shouldRegisterLongHistogram(
      List<Long> bucketBoundaries) {
    LongHistogram histogram =
        metricRegistrar.registerLongHistogram(
            "testHistogram", "description", "unit", bucketBoundaries);
    assertNotNull(histogram);
  }

  @Test
  void test_registerLongGauge_givenLongGaugeName_shouldRegisterLongGauge() {
    metricRegistrar.registerLongGauge("testGauge", "description", "unit", () -> 1L);
    assertNotNull(metricRegistrar.getLongGauge("testGauge"));
  }

  @Test
  void test_registerDoubleGauge_givenDoubleGaugeName_shouldRegisterDoubleGauge() {
    metricRegistrar.registerDoubleGauge("testGauge", "description", "unit", () -> 1.0);
    assertNotNull(metricRegistrar.getDoubleGauge("testGauge"));
  }

  @ParameterizedTest
  @MethodSource("booleanProvider")
  void test_incrementCounter_givenCounterNameAndValue_shouldIncrementCounter(
      boolean counterExists) {
    if (counterExists) {
      metricRegistrar.registerCounter("testCounter", "description", "unit");
    }
    if (counterExists) {
      metricRegistrar.incrementCounter("testCounter", 1L, Attributes.empty());
    } else {
      assertThrows(
          IllegalStateException.class,
          () -> metricRegistrar.incrementCounter("testCounter", 1L, Attributes.empty()));
    }
  }

  @ParameterizedTest
  @MethodSource("booleanProvider")
  void test_updateUpDownCounter_givenUpDownCounterNameAndValue_shouldUpdateUpDownCounter(
      boolean counterExists) {
    if (counterExists) {
      metricRegistrar.registerUpDownCounter("testUpDownCounter", "description", "unit");
    }
    if (counterExists) {
      metricRegistrar.updateUpDownCounter("testUpDownCounter", 1L, Attributes.empty());
    } else {
      assertThrows(
          IllegalStateException.class,
          () -> metricRegistrar.updateUpDownCounter("testUpDownCounter", 1L, Attributes.empty()));
    }
  }

  @ParameterizedTest
  @MethodSource("booleanProvider")
  void test_recordHistogram_givenHistogramNameAndValue_shouldRecordHistogram(
      boolean histogramExists) {
    if (histogramExists) {
      metricRegistrar.registerDoubleHistogram(
          "testHistogram", "description", "unit", Collections.emptyList());
    }
    if (histogramExists) {
      metricRegistrar.recordHistogram("testHistogram", 1.0, Attributes.empty());
    } else {
      assertThrows(
          IllegalStateException.class,
          () -> metricRegistrar.recordHistogram("testHistogram", 1.0, Attributes.empty()));
    }
  }
}
