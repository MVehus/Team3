package projectCard;

/**
 * test
 */
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

    public String toString(){
        return this.value + " with  " + this.priority + " priority. ";
    }

}


