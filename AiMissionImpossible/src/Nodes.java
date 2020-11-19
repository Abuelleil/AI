public class Nodes {
	State current;
	Nodes Parent;
	String Operator;
	int depth;
	int PathCostTotal;

	public Nodes(State curr, Nodes Parent, String op, int depth, int PathCost) {
		this.current = curr;
		this.Parent = Parent;
		this.Operator = op;
		this.depth = depth;
		this.PathCostTotal = PathCost;
	}

	public Nodes(State Inital, Nodes Parent, int depth, int PathCost) {
		this.current = Inital;
		this.Parent = null;
		this.depth = depth;
		this.PathCostTotal = PathCost;
	}
}
