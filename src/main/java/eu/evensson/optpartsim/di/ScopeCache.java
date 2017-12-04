package eu.evensson.optpartsim.di;

import java.util.function.Supplier;

public class ScopeCache<T> {

	private T cache = null;

	public synchronized T get(final Supplier<T> supplier) {
		if (cache == null) {
			cache = supplier.get();
		}
		return cache;
	}

}
