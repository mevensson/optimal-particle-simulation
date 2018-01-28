package eu.evensson.optpartsim.physics;

import static eu.evensson.optpartsim.physics.Vector.polar;
import static eu.evensson.optpartsim.physics.Vector.vector;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

public class CollisionArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<CollisionSources> {

	private CollisionSources collisionSources;

	@Override
	public void accept(final CollisionSources collisionSources) {
		this.collisionSources = collisionSources;
	}

	@Override
	public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
		return Stream.of(collisionSources.value()).map(collisionSource -> provideArgument(collisionSource));
	}

	private Arguments provideArgument(final CollisionSource collisionSource) {
		assertThat(collisionSource.p1Time(), lessThanOrEqualTo(collisionSource.time()));
		assertThat(collisionSource.p2Time(), lessThanOrEqualTo(collisionSource.time()));

		final Vector collisonPosition = vector(collisionSource.x(), collisionSource.y());

		final Vector p1Velocity = polar(collisionSource.p1Vel(), collisionSource.p1Ang());
		final Vector p1CollisionPosition = collisonPosition.subtract(p1Velocity.unit().multiply(Particle.RADIUS));

		final Vector p2Velocity = polar(collisionSource.p2Vel(), collisionSource.p2Ang());
		final Vector p2CollisionPosition = collisonPosition.subtract(p2Velocity.unit().multiply(Particle.RADIUS));

		assertNotEquals(p1Velocity, p2Velocity);

		final Vector p1StartPosition = p1CollisionPosition.subtract(
				p1Velocity.multiply(collisionSource.time() - collisionSource.p1Time()));
		final Vector p2StartPosition = p2CollisionPosition.subtract(
				p2Velocity.multiply(collisionSource.time() - collisionSource.p2Time()));

		return Arguments.of(
				collisionSource.time(),
				new Particle(collisionSource.p1Id(), collisionSource.p1Time(), p1StartPosition, p1Velocity),
				new Particle(collisionSource.p2Id(), collisionSource.p2Time(), p2StartPosition, p2Velocity));
	}

}
