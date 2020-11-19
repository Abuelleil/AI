import java.util.ArrayList;

public class GeneralSearch {
    ArrayList queue = new ArrayList<Nodes>();

    public static boolean Search(Problem problem, String Strategy) {
        if (problem.goalTest()) {
            return true;
        }
        switch (Strategy) {
            case "BF":
                // BFS search
                break;

            case "DF":
                // DFS search
                break;

            case "ID":
                // Iterative Deepening Search
                break;

            case "UC":
                // Uniform Cost Search
                break;

            case "GR1":
                // Greedy search
                break;
            case "GR2":
                // Greedy search
                break;
            case "AS1":
                // A* search
                break;

            case "AS2":
                // A* search
                break;

            default:
                break;
        }
        return false;
    }
}
