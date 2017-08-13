package eu.evensson.optpartsim.simulation;

import eu.evensson.optpartsim.physics.Particle;

public class WallBounceEvent extends Event {

	public static enum Direction {
		HORIZONTAL, VERTICAL
	}

	private Particle particle;
	private Direction direction;

	public WallBounceEvent(final double time, final Particle particle,
			final Direction direction) {
		super(time);
		this.particle = particle;
		this.direction = direction;
	}

	public Particle particle() {
		return particle;
	}

	public Direction direction() {
		return direction;
	}

	// Generated code

	@Override
	public String toString() {
		return "WallBounceEvent [time()=" + time() + ", particle=" + particle
				+ ", direction=" + direction + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result
				+ ((particle == null) ? 0 : particle.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final WallBounceEvent other = (WallBounceEvent) obj;
		if (direction != other.direction)
			return false;
		if (particle == null) {
			if (other.particle != null)
				return false;
		} else if (!particle.equals(other.particle))
			return false;
		return true;
	}

}
