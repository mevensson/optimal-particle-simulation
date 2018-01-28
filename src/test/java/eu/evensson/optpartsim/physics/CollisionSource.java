package eu.evensson.optpartsim.physics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CollisionSource {
	double time() default 0.0;
	double x() default 0.0;
	double y() default 0.0;

	long p1Id() default 1;
	double p1Time() default 0.0;
	double p1Vel() default 1.0;
	double p1Ang() default 0.0;

	long p2Id() default 2;
	double p2Time() default 0.0;
	double p2Vel() default 1.0;
	double p2Ang() default Math.PI;
}
