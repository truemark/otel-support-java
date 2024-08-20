package io.truemark.otel.spring.core.annotations;

import io.truemark.otel.spring.core.config.OpenTelemetryConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OpenTelemetryConfig.class})
public @interface EnableOpenTelemetry {}
