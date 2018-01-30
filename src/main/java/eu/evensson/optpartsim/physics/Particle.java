package eu.evensson.optpartsim.physics;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.util.Optional;

public class Particle {

	public static final double MASS = 1.0;
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

	public double momentum(final Direction direction) {
		final double speed;
		switch (direction) {
		case HORIZONTAL:
			speed = abs(velocity.x());
			break;

		case VERTICAL:
			speed = abs(velocity.y());
			break;

		default:
			throw new InvalidDirectionException();
		}
		return speed * MASS;
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

	public Optional<Double> collisionTime(final Particle other) {
		final Vector pT0 = position.subtract(velocity.multiply(time));
		final Vector pT0Other = other.position.subtract(
				other.velocity.multiply(other.time));
		final Vector dp = pT0.subtract(pT0Other);

		final Vector dv = velocity.subtract(other.velocity);

		final double s = RADIUS + RADIUS;

		final double a = dv.x() * dv.x() + dv.y() * dv.y();
		final double b = 2.0 * (dv.x() * dp.x() + dv.y() * dp.y());
		final double c = dp.x() * dp.x() + dp.y() * dp.y() - s * s;

		final double d = b * b - 4.0 * a * c;
		if (d < 0) {
			return Optional.empty();
		}

		final double t1 = (-b - sqrt(d)) / (2 * a);
		final double t2 = (-b + sqrt(d)) / (2 * a);

		final double minTime = min(t1, t2);
		if (minTime >= max(time, other.time)) {
			return Optional.of(minTime);
		}

		return Optional.empty();
	}

	public Particle collide(final Particle otherParticle) {
		final Optional<Double> collisionTime = collisionTime(otherParticle);
		return collisionTime.map(time -> {
			final Particle p1 = move(time);
			final Particle p2 = otherParticle.move(time);
			final Vector x1MinusX2 = p1.position.subtract(p2.position);
			final Vector v1MinusV2 = p1.velocity.subtract(p2.velocity);
			final double norm = x1MinusX2.norm();
			final Vector velocityChange = x1MinusX2.multiply(v1MinusV2.dotProduct(x1MinusX2) / (norm * norm));
			return new Particle(p1.id, p1.time, p1.position, p1.velocity.subtract(velocityChange));
		}).get();
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
