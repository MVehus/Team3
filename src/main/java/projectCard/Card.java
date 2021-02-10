package projectCard;

public class Card {

    private final int priority;
    private final Value value;

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
