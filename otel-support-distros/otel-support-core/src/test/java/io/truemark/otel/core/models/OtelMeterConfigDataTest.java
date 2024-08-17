package io.truemark.otel.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OtelMeterConfigDataTest {

  @ParameterizedTest
  @CsvSource({"true", "false"})
  public void test_OtelMeterConfigDataConstructorAndGetters_givenVaryingInputs(
      boolean isMeterEnabled) {
    OtelMeterConfigData meterConfig =
        new OtelMeterConfigData(isMeterEnabled, Collections.emptyList());
    assertNotNull(meterConfig.toString());
    assertEquals(isMeterEnabled, meterConfig.isMeterEnabled());
  }
}
