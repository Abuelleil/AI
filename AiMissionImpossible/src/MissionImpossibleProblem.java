import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MissionImpossibleProblem extends Problem {

    static ArrayList<Nodes> queue = new ArrayList<Nodes>();
    public static String m;
    public static String n;
    public static String ex;
    public static String ey;
    public static String sx;
    public static String sy;
    public static String c;
    public static int numberOfNodes;

    enum Operators {
        UP, DOWN, RIGHT, LEFT, CARRY, DROP
    };

    MissionImpossibleState initialState;
    static MissionImpossibleState AllStates;
    Boolean goalTest;
    int PathCost = 0;

    public MissionImpossibleProblem(Enum Operators, MissionImpossibleState initialState,
            MissionImpossibleState AllStates, Boolean GoalTest, int pathCost) {
        super(Operators, initialState, AllStates, GoalTest, pathCost);
    }

    static int NumberImf;
    static HashMap<String, Integer> CarriedMembers;
    static HashMap<String, Integer> IMFMembers;

    public static String genGrid() {
        String Grid = "";

        int gridx = (int) (Math.random() * (15 - 5 + 1) + 5);
        int gridy = (int) (Math.random() * (15 - 5 + 1) + 5);

        String gridWidth = "" + gridx;
        String gridHeight = "" + gridy;
        Grid += gridWidth + "," + gridHeight + ";";

        HashSet<String> set = new HashSet<>();

        String EthanX = "" + (int) Math.abs((Math.random() * 10 - gridx));
        String EthanY = "" + (int) Math.abs((Math.random() * 10 - gridy));
        Grid += EthanX + "," + EthanY + ";";
        set.add(EthanX + "" + EthanY);

        String SubX = "" + (int) Math.abs((Math.random() * 10 - gridx));
        String SubY = "" + (int) Math.abs((Math.random() * 10 - gridx));
        while (set.contains(SubX + "" + SubY)) {
            SubX = "" + (int) Math.abs((Math.random() * 10 - gridx));
            SubY = "" + (int) Math.abs((Math.random() * 10 - gridx));
        }
        Grid += SubX + "," + SubY + ";";
        set.add(SubX + "" + SubY);

        NumberImf = (int) (Math.random() * (10 - 5 + 1) + 5);
        String IMFLocations = "";
        String Health = "";

        for (int i = 0; i < NumberImf; i++) {

            String IMFX = "" + (int) Math.abs((Math.random() * 10 - gridx));
            String IMFY = "" + (int) Math.abs((Math.random() * 10 - gridy));
            while (set.contains(IMFX + "" + IMFY)) {
                IMFX = "" + (int) Math.abs((Math.random() * 10 - gridx));
                IMFY = "" + (int) Math.abs((Math.random() * 10 - gridx));
            }
            String IMFHealth = "" + (int) (1 + Math.random() * 100);
            if (i < NumberImf - 1) {
                IMFLocations += IMFX + "," + IMFY + ",";
                Health += IMFHealth + ",";
            } else {
                IMFLocations += IMFX + "," + IMFY + ";";
                Health += IMFHealth + ";";
            }
        }

        Grid += IMFLocations;
        Grid += Health;

        int CarryCapacity = (int) (Math.random() * (10 - 5 + 1) + 5);
        Grid += CarryCapacity;
        return Grid;
    }

    public static void solve(String Grid, String Strategy, boolean Visualize) {
        int deaths = 0;
        StringTokenizer st = new StringTokenizer(Grid, ";,");
        m = st.nextToken();
        n = st.nextToken();
        ex = st.nextToken();
        ey = st.nextToken();
        sx = st.nextToken();
        sy = st.nextToken();
        String[] IMFLocations = new String[NumberImf];
        int[] IMFHealth = new int[NumberImf];

        for (int i = 0; i < NumberImf; i++) {
            String x = st.nextToken();
            String y = st.nextToken();
            IMFLocations[i] = x + "," + y;
        }
        for (int i = 0; i < NumberImf; i++) {
            IMFHealth[i] = Integer.parseInt(st.nextToken());
        }
        c = st.nextToken();

        for (int i = 0; i < NumberImf; i++) {
            IMFMembers.put(IMFLocations[i], IMFHealth[i]);
        }

        MissionImpossibleState initialState = new MissionImpossibleState(ex + "," + ey, IMFMembers);
        IMFMembers.clear();
        Nodes RootNode = new Nodes(initialState, null, 0, 2);
        queue.add(RootNode);
        MissionImpossibleProblem Problem = new MissionImpossibleProblem(Operators, initialState, AllStates, false, 2);
        Nodes Solution = Search(Problem, Strategy);
        if (Solution == null) {
            System.out.println("No solution was found");
        }
        // if (Visualize) {

        // for (int i = 0; i < IMFHealth.length; i++) {
        // if (IMFHealth[i] == 0) {
        // deaths++;
        // System.out.println(deaths);
        // } else {
        // System.out.print(IMFHealth[i]);
        // }
        // }
    }

    static Nodes Search(MissionImpossibleProblem problem, String Strategy) {
        while (true) {
            Nodes curr = (Nodes) queue.get(0);
            if (problem.goalTest()) {
                return curr;
            }
            if (queue.isEmpty()) {
                return null;
            }
            curr = (Nodes) queue.remove(0);
            switch (Strategy) {
                case "BF":
                    ArrayList<Nodes> temp = Expand(curr);
                    for (int i = 0; i < temp.size(); i++) {
                        queue.add(temp.get(i));
                    }
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
        }
    }

    public static ArrayList<Nodes> Expand(Nodes node) {

        ArrayList<Nodes> temp = new ArrayList<Nodes>();
        MissionImpossibleState currState = (MissionImpossibleState) node.current;
        int depth = node.depth + 1;
        Nodes DownNode;
        Nodes UpNode;
        Nodes RightNode;
        Nodes LeftNode;
        // check if ethan is in a corner
        if (currState.Ethan.equals("0,0") || currState.Ethan.equals(m + ",0") || currState.Ethan.equals("0," + n)
                || currState.Ethan.equals(m + "," + n)) {

            if (currState.Ethan.equals("0,0")) {
                DownNode = new Nodes(new MissionImpossibleState(moveDown(currState), IMFMembers), node, "DOWN", depth,
                        2);

                RightNode = new Nodes(new MissionImpossibleState(moveRight(currState), IMFMembers), node, "RIGHT",
                        depth, 2);

                temp.add(DownNode);

                temp.add(RightNode);

                numberOfNodes += 2;

            }
            if (currState.Ethan.equals(m + ",0")) {
                DownNode = new Nodes(new MissionImpossibleState(moveDown(currState), IMFMembers), node, "DOWN", depth,
                        2);

                LeftNode = new Nodes(new MissionImpossibleState(moveLeft(currState), IMFMembers), node, "LEFT", depth,
                        2);

                temp.add(DownNode);

                temp.add(LeftNode);

                numberOfNodes += 2;
            }
            if (currState.Ethan.equals("0," + n)) {
                UpNode = new Nodes(new MissionImpossibleState(moveUp(currState), IMFMembers), node, "UP", depth, 2);

                RightNode = new Nodes(new MissionImpossibleState(moveRight(currState), IMFMembers), node, "RIGHT",
                        depth, 2);

                temp.add(UpNode);

                temp.add(RightNode);
                numberOfNodes += 2;
            }
            if (currState.Ethan.equals(m + "," + n)) {
                UpNode = new Nodes(new MissionImpossibleState(moveUp(currState), IMFMembers), node, "UP", depth, 2);

                LeftNode = new Nodes(new MissionImpossibleState(moveLeft(currState), IMFMembers), node, "LEFT", depth,
                        2);

                temp.add(UpNode);

                temp.add(LeftNode);

                numberOfNodes += 2;
            }
        } else {
            DownNode = new Nodes(new MissionImpossibleState(moveDown(currState), IMFMembers), node, "DOWN", depth, 2);

            UpNode = new Nodes(new MissionImpossibleState(moveUp(currState), IMFMembers), node, "UP", depth, 2);

            RightNode = new Nodes(new MissionImpossibleState(moveRight(currState), IMFMembers), node, "RIGHT", depth,
                    2);

            LeftNode = new Nodes(new MissionImpossibleState(moveLeft(currState), IMFMembers), node, "LEFT", depth, 2);

            temp.add(UpNode);

            temp.add(LeftNode);

            temp.add(DownNode);

            temp.add(RightNode);

            numberOfNodes += 4;

        }

        // check submarine
        if (currState.Ethan.equals(sx + "," + sy) && CarriedMembers.size() <= Integer.parseInt(c)) {
            CarriedMembers.clear();
            MissionImpossibleState fx = new MissionImpossibleState(currState.Ethan, IMFMembers);
            Nodes DropNode = new Nodes(fx, node, "DROP", depth, 2);
            numberOfNodes++;
            temp.add(DropNode);
        }

        // check if IMF member in the same cell && carry them if possible
        if (IMFMembers.containsKey(currState.Ethan) && (CarriedMembers.size() < Integer.parseInt(c))) {
            String Loc = currState.Ethan;
            int life = IMFMembers.get(currState.Ethan);
            CarriedMembers.put(Loc, life);
            IMFMembers.remove(Loc);
            MissionImpossibleState fx = new MissionImpossibleState(Loc, IMFMembers);
            Nodes CarryNode = new Nodes(fx, node, "CARRY", depth, 2);
            numberOfNodes++;
            temp.add(CarryNode);
        }
        return temp;
    }

    static String moveUp(MissionImpossibleState currState) {
        int x = Integer.parseInt(currState.Ethan.substring(0, 1));
        int y = Integer.parseInt(currState.Ethan.substring(2));
        y = y - 1;
        String newPos = x + "," + y;
        return newPos;
    }

    static String moveDown(MissionImpossibleState currState) {
        int x = Integer.parseInt(currState.Ethan.substring(0, 1));
        int y = Integer.parseInt(currState.Ethan.substring(2));
        y = y + 1;
        String newPos = x + "," + y;
        return newPos;
    }

    static String moveRight(MissionImpossibleState currState) {
        int x = Integer.parseInt(currState.Ethan.substring(0, 1));
        int y = Integer.parseInt(currState.Ethan.substring(2));
        x = x + 1;
        String newPos = x + "," + y;
        return newPos;

    }

    static String moveLeft(MissionImpossibleState currState) {
        int x = Integer.parseInt(currState.Ethan.substring(0, 1));
        int y = Integer.parseInt(currState.Ethan.substring(2));
        x = x - 1;
        String newPos = x + "," + y;
        return newPos;
    }

    @Override
    Boolean goalTest() {
        Nodes x = (Nodes) queue.get(0);
        MissionImpossibleState y = (MissionImpossibleState) x.current;
        if (y.IMFMembers.isEmpty()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String x = genGrid();
        System.out.println(x);
        solve(x, "asdjhakjsd", false);
    }
}
