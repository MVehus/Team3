package projectCard;

/**
 * Card object,
 * Each Program card contains a priority and a value.
 */
public class ProgramCard {

    private final int priority;
    public final Value value;

    public ProgramCard(int priority, Value value){
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
        return (this.value + " with  " + this.priority + " priority. ");
    }
}


