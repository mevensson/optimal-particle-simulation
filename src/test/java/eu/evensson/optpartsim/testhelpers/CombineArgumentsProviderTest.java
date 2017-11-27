package eu.evensson.optpartsim.testhelpers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CombineArgumentsProviderTest {

	enum TestEnum {
		Value1, Value2
	}

	@ParameterizedTest(name = "anEnum={0}, aLong={1}, aString={2}")
	@CombineSource(sources = {
			@CombinableSource(enumSource =
					@EnumSource(TestEnum.class)),
			@CombinableSource(longRangeSource =
					@LongRangeSource(min = 1, max = 5)),
			@CombinableSource(valueSource =
					@ValueSource(strings = { "String1", "String2" })),
	})
	void exampleTest(final TestEnum anEnum, final long aLong, final String aString) {
	}
}
