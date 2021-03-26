package projectCard;

import java.io.Serializable;

/**
 * Card object,
 * Each Program card contains a priority and a value.
 */
public class ProgramCard implements Serializable {

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

    public String getCardImagePath() {
        return ("src/assets/programCards/" + this.value + "/" + this.value + "_" + this.priority + ".jpg");
    }

    public String toString(){
        return (this.priority + " priority for " + this.value);
    }
}


