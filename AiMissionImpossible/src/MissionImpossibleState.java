import java.util.HashMap;

public class MissionImpossibleState extends State {
    String Ethan;
    HashMap<String, Integer> IMFMembers;

    public MissionImpossibleState(String Ethan, HashMap<String, Integer> IMFMembers) {
        this.Ethan = Ethan;
        this.IMFMembers = IMFMembers;

    }

    // public State(){
    // this.IMFMembers = new HashMap<>();
    // }
}