package eu.evensson.optpartsim.simulation;

import eu.evensson.optpartsim.physics.Box;

public class SimulationInjector {

	public static Simulation injectSimulation(final SimulationScope scope) {
		return new DefaultSimulation(injectCellStructure(scope), injectEventQueue(scope),
				injectEventChecker(scope), injectEventHandler(scope));
	}

	private static CellStructure injectCellStructure(final SimulationScope scope) {
		return scope.getCellStucture(() -> injectFreshCellStructure(scope));
	}

	private static CellStructure injectFreshCellStructure(final SimulationScope scope) {
		return new CellStructure(injectWalls(scope), 1, 1);
	}

	private static Box injectWalls(final SimulationScope scope) {
		return new Box(0.0, 0.0, scope.getWidth(), scope.getHeight());
	}

	private static EventQueue injectEventQueue(final SimulationScope scope) {
		return scope.getEventQueue(() -> injectFreshEventQueue());
	}

	private static EventQueue injectFreshEventQueue() {
		return new EventQueue();
	}

	private static EventChecker injectEventChecker(final SimulationScope scope) {
		return scope.getEventChecker(() -> injectFreshEventChecker(scope));
	}

	private static EventChecker injectFreshEventChecker(final SimulationScope scope) {
		return new EventChecker(injectCellStructure(scope));
	}

	private static EventHandler injectEventHandler(final SimulationScope scope) {
		return new EventHandler(injectCellStructure(scope), injectEventChecker(scope), injectEventQueue(scope));
	}

}
