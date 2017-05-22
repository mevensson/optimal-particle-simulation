package eu.evensson.optpartsim;

public class Event implements Comparable<Event> {

	private final double time;

	public Event(final double time) {
		this.time = time;
	}

	public double time() {
		return time;
	}

	@Override
	public int compareTo(final Event o) {
		return Double.compare(time, o.time);
	}

	// Generated methods

	@Override
	public String toString() {
		return "Event [time=" + time + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(time);
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
		final Event other = (Event) obj;
		if (Double.doubleToLongBits(time) != Double
				.doubleToLongBits(other.time))
			return false;
		return true;
	}

}
