package eu.evensson.optpartsim.simulation;

import eu.evensson.optpartsim.physics.Particle;

public class CollisionEvent extends Event {

	private Particle particle;
	private Particle otherParticle;

	public CollisionEvent(final double time, final Particle particle,
			final Particle otherParticle) {
		super(time);
		this.particle = particle;
		this.otherParticle = otherParticle;
	}

	public Particle particle() {
		return particle;
	}

	public Particle otherParticle() {
		return otherParticle;
	}

	// Generated code

	@Override
	public String toString() {
		return "CollisionEvent [time()=" + time() + ", particle=" + particle
				+ ", otherParticle=" + otherParticle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((otherParticle == null) ? 0 : otherParticle.hashCode());
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
		final CollisionEvent other = (CollisionEvent) obj;
		if (otherParticle == null) {
			if (other.otherParticle != null)
				return false;
		} else if (!otherParticle.equals(other.otherParticle))
			return false;
		if (particle == null) {
			if (other.particle != null)
				return false;
		} else if (!particle.equals(other.particle))
			return false;
		return true;
	}


}
