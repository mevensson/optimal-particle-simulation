package eu.evensson.optpartsim.physics;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.max;

public class Particle {

	public static final double RADIUS = 1.0;

	public static enum Direction {
		HORIZONTAL, VERTICAL
	}

	@SuppressWarnings("serial")
	public static class InvalidDirectionException extends RuntimeException {
	}

	private final long id;
	private final double time;
	private final Vector position;
	private final Vector velocity;

	public Particle(final long id, final double time, final Vector position,
			final Vector velocity) {
		this.id = id;
		this.time = time;
		this.position = position;
		this.velocity = velocity;
	}

	public long id() {
		return id;
	}

	public double time() {
		return time;
	}

	public Vector position() {
		return position;
	}

	public Vector velocity() {
		return velocity;
	}

	public Particle bounce(final Direction direction) {
		final Vector newVelocity;
		switch (direction) {
		case HORIZONTAL:
			newVelocity = vector(-velocity.x(), velocity.y());
			break;
		case VERTICAL:
			newVelocity = vector(velocity.x(), -velocity.y());
			break;
		default:
			throw new InvalidDirectionException();
		}
		return new Particle(id, time, position, newVelocity);
	}

	public double collisionTime(final Box box, final Direction direction) {
		switch (direction) {
		case HORIZONTAL:
			final double distanceLeft = position.x() - box.x() - RADIUS;
			final double timeLeft = distanceLeft / -velocity.x();
			final double distanceRight = box.x() + box.width() - position.x()
					- RADIUS;
			final double timeRight = distanceRight / velocity.x();
			return time + max(timeLeft, timeRight);

		case VERTICAL:
			final double distanceTop = position.y() - box.y() - RADIUS;
			final double timeTop = distanceTop / -velocity.y();
			final double distanceBottom = box.y() + box.height() - position.y()
					- RADIUS;
			final double timeBottom = distanceBottom / velocity.y();
			return time + max(timeTop, timeBottom);

		default:
			throw new InvalidDirectionException();
		}
	}

	public Particle move(final double newTime) {
		final Vector movedDistance = velocity.multiply(newTime - time);
		final Vector newPosition = position.add(movedDistance);
		return new Particle(id, newTime, newPosition, velocity);
	}

	// Generated methods below

	@Override
	public String toString() {
		return "Particle [id=" + id + ", time=" + time + ", position="
				+ position + ", velocity=" + velocity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		long temp;
		temp = Double.doubleToLongBits(time);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(time) != Double
				.doubleToLongBits(other.time))
			return false;
		if (velocity == null) {
			if (other.velocity != null)
				return false;
		} else if (!velocity.equals(other.velocity))
			return false;
		return true;
	}

}
