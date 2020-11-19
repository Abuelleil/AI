import java.util.HashMap;

public abstract class Problem {

	static Enum Operators;

	State initialState;

	State allStates;

	abstract Boolean goalTest();

	int PathCost;

	public Problem(Enum Operators, State initialState, State allStates, boolean GoalTest, int pathCost) {
		Problem.Operators = Operators;
		this.allStates = allStates;
		this.PathCost += pathCost;
		this.initialState = initialState;
	}
}
