package eu.evensson.optpartsim.testhelpers;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

public class LongRangeArgumentsProvider
		implements ArgumentsProvider, AnnotationConsumer<LongRangeSource> {

	private LongRangeSource annotation;

	@Override
	public void accept(final LongRangeSource annotation) {
		this.annotation = annotation;
	}

	@Override
	public Stream<? extends Arguments> provideArguments(
			final ExtensionContext context) throws Exception {
		return LongStream
				.rangeClosed(annotation.min(), annotation.max())
				.mapToObj(v -> Arguments.of(v));
	}

}
