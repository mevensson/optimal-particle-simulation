package eu.evensson.optpartsim.simulation;

import eu.evensson.optpartsim.physics.Box;

public class SimulationInjector {

	public static Simulation injectSimulation(final SimulationScope scope) {
		return new DefaultSimulation(injectCellStructure(scope), injectEventQueue(),
				injectEventChecker(scope));
	}

	private static CellStructure injectCellStructure(final SimulationScope scope) {
		return new CellStructure(injectWalls(scope), 1, 1);
	}

	private static Box injectWalls(final SimulationScope scope) {
		return new Box(0.0, 0.0, scope.getWidth(), scope.getHeight());
	}

	private static EventQueue injectEventQueue() {
		return new EventQueue();
	}


	private static EventChecker injectEventChecker(final SimulationScope scope) {
		return new EventChecker(injectCellStructure(scope));
	}
}
