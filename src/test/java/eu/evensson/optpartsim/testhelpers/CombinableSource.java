package eu.evensson.optpartsim.testhelpers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CombinableSource {
	EnumSource[] enumSource() default {};
	LongRangeSource[] longRangeSource() default {};
	ValueSource[] valueSource() default {};
}
