package projectCard;

import java.util.ArrayList;

public class CardDeck {
    private ArrayList<Card> deck = new ArrayList<>();

    public CardDeck(){
        createDeck();
    }

    /**
     * Program Card Deck contains following:
     *  Move Cards
     * 18 ...   Move 1
     * 12 ...   Move 2
     * 6 ...    Move 3
     * 6 ...    Back Up
     *
     *  Rotate Cards
     * 18 ...   Rotate Right
     * 18 ...   Rotate Left
     * 6 ...    U turn
     */
    private void createDeck(){
        for (int i = 0; i < 18; i++){
            // TODO
        }
    }
}
