package projectCard;

import java.util.ArrayList;
import java.util.Random;

public class CardDeck {

    private ArrayList<Card> deck;

    public CardDeck(){
        this.deck = new ArrayList<>();
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
        Random r = new Random();

        // Add 18 Move 1, Rotate Right, Rotate Left
        for (int i = 0; i < 18; i++){

            deck.add(new Card(r.nextInt(200) + 100, Value.MOVE_ONE));
            deck.add(new Card(r.nextInt(200) + 100, Value.ROTATE_RIGHT));
            deck.add(new Card(r.nextInt(200) + 100, Value.ROTATE_LEFT));
        }

        // Add 12 Move 2
        for (int i = 0; i < 12; i++){
            deck.add(new Card(r.nextInt(300) + 200, Value.MOVE_TWO));
        }

        // Add 6 Move 3, Back-Up and U-Turn
        for (int i = 0; i < 6; i++){
            deck.add(new Card(r.nextInt(800) + 700, Value.MOVE_THREE));
            deck.add(new Card(r.nextInt(700) + 600, Value.BACK_UP));
            deck.add(new Card(r.nextInt(100) + 10, Value.U_TURN));
        }
    }

    public ArrayList<Card> getDeck(){
        return deck;
    }

    public int getTotalCards(){
        return deck.size();
    }

    public ArrayList<Card> drawCards(int totalCards){
        Random r = new Random();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < totalCards; i++){
            int index = r.nextInt(deck.size());
            Card card = deck.get(index);
            deck.remove(index);
            cards.add(card);
        }

        return cards;
    }

    public void shuffle(){
        deck.clear();
        createDeck();
    }

}
