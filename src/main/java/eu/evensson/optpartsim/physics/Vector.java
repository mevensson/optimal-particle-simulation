package eu.evensson.optpartsim.physics;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Vector {

	public static Vector vector(final double x, final double y) {
		return new Vector(x, y);
	}

	public static Vector polar(final double distance, final double angle) {
		return vector(distance * cos(angle), distance * sin(angle));
	}

	private final double x;
	private final double y;

	private Vector(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public Vector add(final Vector otherVector) {
		return new Vector(x + otherVector.x, y + otherVector.y);
	}

	public Vector multiply(final double scalar) {
		return new Vector(x * scalar, y * scalar);
	}

	public Vector subtract(final Vector otherVector) {
		return new Vector(x - otherVector.x, y - otherVector.y);
	}

	// Generated methods below
	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
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
		final Vector other = (Vector) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
