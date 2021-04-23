package projectCard;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {

    private ArrayList<ProgramCard> hand;

    public Hand(ArrayList<ProgramCard> cards) {
        if (cards.size() <= 9) {
            hand = cards;
        } else {
            System.out.println("No more than 9 cards are allowed as a hand");
        }
    }

    public ArrayList<ProgramCard> getCards() {
        return hand == null ? new ArrayList<ProgramCard>() : hand;
    }
}
