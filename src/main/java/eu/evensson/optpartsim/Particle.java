package eu.evensson.optpartsim;

public class Particle {

	private final long id;
	private final Vector position;
	private final Vector velocity;

	public Particle(final long id, final Vector position,
			final Vector velocity) {
		this.id = id;
		this.position = position;
		this.velocity = velocity;
	}

	public long id() {
		return id;
	}

	public Vector position() {
		return position;
	}

	public Vector velocity() {
		return velocity;
	}

	// Generated methods below
	@Override
	public String toString() {
		return "Particle [id=" + id + ", position=" + position + ", velocity="
				+ velocity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((velocity == null) ? 0 : velocity.hashCode());
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
		final Particle other = (Particle) obj;
		if (id != other.id)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (velocity == null) {
			if (other.velocity != null)
				return false;
		} else if (!velocity.equals(other.velocity))
			return false;
		return true;
	}

}
