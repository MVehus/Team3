package projectCard;

import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * card deck
 */
public class CardDeck {

    /**
     * Store deck in a HashMap, store availability in value (True if card is available, False if card is already used)
     */
    private final ArrayList<ProgramCard> availableCards;
    private final ArrayList<ProgramCard> usedCards;

    public CardDeck(){
        this.availableCards = new ArrayList<>();
        this.usedCards = new ArrayList<>();
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
        for (int i = 1; i <= 84; i++){
            if (i<=6) {
                availableCards.add(new ProgramCard(i*10, Value.U_TURN));
            }
            else if (i<=42) {
                if (i%2==0) {
                    availableCards.add(new ProgramCard(i*10, Value.ROTATE_RIGHT));
                }
                else {
                    availableCards.add(new ProgramCard(i*10, Value.ROTATE_LEFT));
                }
            }
            else if (i<=48) {
                availableCards.add(new ProgramCard(i*10, Value.BACK_UP));
            }
            else if (i<=66) {
                availableCards.add(new ProgramCard(i*10, Value.MOVE_ONE));
            }
            else if (i<=78) {
                availableCards.add(new ProgramCard(i*10, Value.MOVE_TWO));
            }
            else {
                availableCards.add(new ProgramCard(i*10, Value.MOVE_THREE));
            }
        }
    }

    /**
     * Draw n cards
     *
     * -> Input vil være => n = 9 - damageTokens
     *
     * @param totalCards - total cards
     * @return List of n cards
     */
    public ArrayList<ProgramCard> drawCards(int totalCards){
        Random r = new Random();
        ArrayList<ProgramCard> cards = new ArrayList<>();
        for (int i = 0; i < totalCards; i++) {
            int index = r.nextInt(availableCards.size());
            ProgramCard card = availableCards.get(index);
            cards.add(card);
            usedCards.add(card);
            availableCards.remove(card);
        }
        return cards;
    }

    /**
     * @return deck
     */
    public ArrayList<ProgramCard> getAvailableCards(){
        return availableCards;
    }

    /**
     * @return total cards
     */
    public int getDeckSize(){
        return availableCards.size();
    }

    public void restock(){
        availableCards.addAll(usedCards);
    }


}
