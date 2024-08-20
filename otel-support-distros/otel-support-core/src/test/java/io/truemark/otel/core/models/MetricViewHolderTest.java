package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import io.opentelemetry.sdk.metrics.InstrumentSelector;
import io.opentelemetry.sdk.metrics.View;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MetricViewHolderTest {

  static Stream<Arguments> provideSelectorAndView() {
    return Stream.of(Arguments.of(mock(InstrumentSelector.class), mock(View.class)));
  }

  @ParameterizedTest
  @MethodSource("provideSelectorAndView")
  void test_MetcriViewConstructorAndGetters_givenVaryingInputs(
      InstrumentSelector selector, View view) {
    MetricViewHolder metricViewHolder = new MetricViewHolder(selector, view);

    assertNotNull(metricViewHolder);
    assertEquals(selector, metricViewHolder.getSelector());
    assertEquals(view, metricViewHolder.getView());
  }
}
