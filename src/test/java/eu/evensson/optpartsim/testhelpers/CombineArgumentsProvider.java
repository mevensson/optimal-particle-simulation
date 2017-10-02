package eu.evensson.optpartsim.testhelpers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

public class CombineArgumentsProvider
		implements ArgumentsProvider, AnnotationConsumer<CombineSource> {

	private CombineSource annotation;

	@Override
	public void accept(final CombineSource annotation) {
		this.annotation = annotation;
	}

	@Override
	public Stream<? extends Arguments> provideArguments(
			final ExtensionContext context) throws Exception {
		final List<Stream<? extends Arguments>> argumentsStreams = new ArrayList<>();
		for (final LongRangeSource longRangeSource : annotation.sources()) {
			final LongRangeArgumentsProvider provider = getProvider(longRangeSource);
			argumentsStreams.add(provider.provideArguments(context));
		}
		return allCombinations(argumentsStreams);
	}

	private LongRangeArgumentsProvider getProvider(final LongRangeSource annotation) {
		final LongRangeArgumentsProvider argumentsProvider = new LongRangeArgumentsProvider();
		argumentsProvider.accept(annotation);
		return argumentsProvider;
	}

	private Stream<? extends Arguments> allCombinations(
			final List<Stream<? extends Arguments>> streams) {
		final List<List<? extends Arguments>> lists = new ArrayList<>();
		for (final Stream<? extends Arguments> stream : streams) {
			lists.add(stream.collect(Collectors.toList()));
		}
		return allCombinations(lists, emptyList());
	}

	private Stream<? extends Arguments> allCombinations(
			final List<List<? extends Arguments>> streams,
			final List<Object> current) {
		if (streams.isEmpty()) {
			return Stream.of(() -> current.toArray());
		}
		return streams.get(0).stream().flatMap(e -> {
			final List<Object> list = new ArrayList<>(current);
			list.addAll(asList(e.get()));
			return allCombinations(streams.subList(1, streams.size()), list);
		});
	}
}
