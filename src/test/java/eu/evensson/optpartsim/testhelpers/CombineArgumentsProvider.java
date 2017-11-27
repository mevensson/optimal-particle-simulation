package eu.evensson.optpartsim.testhelpers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.util.ReflectionUtils;

public class CombineArgumentsProvider
		implements ArgumentsProvider, AnnotationConsumer<CombineSource> {

	private static ArgumentsProvider initializeProvider(
			final ArgumentsProvider provider, final Annotation annotation) {
		if (provider instanceof AnnotationConsumer) {
			@SuppressWarnings("unchecked")
			final
			AnnotationConsumer<Annotation> consumer = (AnnotationConsumer<Annotation>) provider;
			consumer.accept(annotation);
		}
		return provider;
	}

	private CombineSource annotation;

	@Override
	public void accept(final CombineSource annotation) {
		this.annotation = annotation;
	}

	@Override
	public Stream<? extends Arguments> provideArguments(
			final ExtensionContext context) throws Exception {
		final List<Stream<? extends Arguments>> argumentsStreams = new ArrayList<>();
		for (final CombinableSource combinableSource : annotation.sources()) {
			final Stream<? extends Arguments> argumentStream =
					getProviders(combinableSource).flatMap(provider -> provideArguments(provider, context));
			argumentsStreams.add(argumentStream);
		}
		return allCombinations(argumentsStreams);
	}

	private Stream<ArgumentsProvider> getProviders(final CombinableSource combinableSource) {
		return Stream.of(
				Arrays.stream(combinableSource.enumSource()),
				Arrays.stream(combinableSource.longRangeSource()),
				Arrays.stream(combinableSource.valueSource()))
			.flatMap(annotation -> annotation)
			.flatMap(annotation -> getProviders(annotation));
	}

	private Stream<ArgumentsProvider> getProviders(final Annotation annotation) {
		final Stream<ArgumentsSource> stream = Arrays.stream(annotation.annotationType().getAnnotationsByType(ArgumentsSource.class));
		final Stream<Class<? extends ArgumentsProvider>> stream2 = stream.map(ArgumentsSource::value);
		final Stream<? extends ArgumentsProvider> stream3 = stream2.map(ReflectionUtils::newInstance);
		final Stream<ArgumentsProvider> stream4 = stream3.map(provider -> initializeProvider(provider, annotation));
		return stream4;
	}

	private Stream<? extends Arguments> provideArguments(final ArgumentsProvider provider, final ExtensionContext context) {
		try {
			return provider.provideArguments(context);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
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
