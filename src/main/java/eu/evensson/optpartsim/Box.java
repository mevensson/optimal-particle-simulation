package eu.evensson.optpartsim;

public class Box {

	private final double x;
	private final double y;
	private final double width;
	private final double height;

	public Box(final double x, final double y, final double width,
			final double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	// Generated methods below

	@Override
	public String toString() {
		return "Box [x=" + x + ", y=" + y + ", width=" + width + ", height="
				+ height + "]";
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
		final Box other = (Box) obj;
		if (Double.doubleToLongBits(height) != Double
				.doubleToLongBits(other.height))
			return false;
		if (Double.doubleToLongBits(width) != Double
				.doubleToLongBits(other.width))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
