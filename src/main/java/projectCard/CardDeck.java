package projectCard;

import org.lwjgl.system.CallbackI;
import player.Player;

import java.util.*;

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
        // Add 18 Move 1, Rotate Right, Rotate Left
        for (int i = 0; i < 18; i++){
            ProgramCard move_one = new ProgramCard(randomIntInRange(100, 200), Value.MOVE_ONE);
            ProgramCard rotate_right = new ProgramCard(randomIntInRange(100, 200), Value.ROTATE_RIGHT);
            ProgramCard rotate_left = new ProgramCard(randomIntInRange(100, 200), Value.ROTATE_LEFT);
            availableCards.add(move_one);
            availableCards.add(rotate_right);
            availableCards.add(rotate_left);
        }
        // Add 12 Move 2
        for (int i = 0; i < 12; i++){
            ProgramCard move_two = new ProgramCard(randomIntInRange(200, 300), Value.MOVE_TWO);
            availableCards.add(move_two);
        }
        // Add 6 Move 3, Back-Up and U-Turn
        for (int i = 0; i < 6; i++){
            ProgramCard move_three = new ProgramCard(randomIntInRange(700, 800), Value.MOVE_THREE);
            ProgramCard back_up = new ProgramCard(randomIntInRange(600, 700), Value.BACK_UP);
            ProgramCard u_turn = new ProgramCard(randomIntInRange(10, 100), Value.U_TURN);
            availableCards.add(move_three);
            availableCards.add(back_up);
            availableCards.add(u_turn);
        }
    }

    private int randomIntInRange(int min, int max){
        return (min + (int)(Math.random() * ((max - min) + 1)));
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

    public int getUsedCardsSize(){
        return usedCards.size();
    }

    public void restock(){
        availableCards.addAll(usedCards);
        usedCards.clear();
    }


    public void dealCards(List<Player> players) {
        Collections.shuffle(availableCards);
        for(Player p : players) {
            if(!p.inPowerDown())
                p.cards = drawCards(9- p.getNumDamageTokens());
        }
    }

}
