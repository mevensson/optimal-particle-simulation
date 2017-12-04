package eu.evensson.optpartsim.simulation;

import java.util.function.Supplier;

import eu.evensson.optpartsim.di.ScopeCache;

public class SimulationScope {

	private final ScopeCache<CellStructure> cellStructureCache = new ScopeCache<>();
	private final ScopeCache<EventChecker> eventCheckerCache = new ScopeCache<>();
	private final ScopeCache<EventQueue> eventQueueCache = new ScopeCache<>();

	private final double height;
	private final double width;

	public SimulationScope(final double width, final double height) {
		this.width = width;
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public CellStructure getCellStucture(final Supplier<CellStructure> freshCellStructureSupplier) {
		return cellStructureCache.get(freshCellStructureSupplier);
	}

	public EventChecker getEventChecker(final Supplier<EventChecker> freshEventCheckerSupplier) {
		return eventCheckerCache.get(freshEventCheckerSupplier);
	}

	public EventQueue getEventQueue(final Supplier<EventQueue> freshEventQueueSupplier) {
		return eventQueueCache.get(freshEventQueueSupplier);
	}

	// Generated method

	@Override
	public String toString() {
		return "SimulationScope [height=" + height + ", width=" + width + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(width);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimulationScope other = (SimulationScope) obj;
		if (Double.doubleToLongBits(height) != Double
				.doubleToLongBits(other.height))
			return false;
		if (Double.doubleToLongBits(width) != Double
				.doubleToLongBits(other.width))
			return false;
		return true;
	}
}
