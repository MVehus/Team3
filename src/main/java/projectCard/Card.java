package projectCard;

public class Card {

    private final int priority;
    public final Value value;

    public Card(int priority, Value value){
        this.priority = priority;
        this.value = value;
    }

    public Value getValue(){
        return value;
    }

    public int getPriority(){
        return priority;
    }

}

enum Value {
    MOVE_THREE,
    MOVE_TWO,
    MOVE_ONE,
    BACK_UP,
    U_TURN,
    ROTATE_LEFT,
    ROTATE_RIGHT
}

