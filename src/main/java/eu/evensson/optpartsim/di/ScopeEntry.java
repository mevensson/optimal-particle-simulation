package eu.evensson.optpartsim.di;

public interface ScopeEntry<Scope, Result> {

	Result enter(Scope scope);

}
