package io.truemark.otel.core.models;

import io.truemark.otel.core.models.OtelMeterConfigData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class OtelMeterConfigDataTest {

    @ParameterizedTest
    @CsvSource({
        "true",
        "false"
    })
    public void test_OtelMeterConfigDataConstructorAndGetters_givenVaryingInputs(boolean isMeterEnabled) {
        OtelMeterConfigData meterConfig = new OtelMeterConfigData(isMeterEnabled);
        assertNotNull(meterConfig.toString());
        assertEquals(isMeterEnabled, meterConfig.isMeterEnabled());
    }

}