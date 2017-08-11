package eu.evensson.optpartsim;

public class EventChecker {

	private CellStructure cellStructure;

	public EventChecker(final CellStructure cellStructure) {
		this.cellStructure = cellStructure;
	}

	public Event check(final Particle particle) {
		final Box walls = cellStructure.getWalls();
		final double distance = particle.position().x() - walls.x();
		return new WallBounceEvent(distance / -particle.velocity().x());
	}

}
