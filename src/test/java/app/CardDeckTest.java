package app;

import org.junit.Test;
import projectCard.ProgramCard;
import projectCard.CardDeck;
import projectCard.Value;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CardDeckTest {

    private final CardDeck DECK = new CardDeck();

    @Test
    public void FullDeckContains84Cards(){
        assertEquals(84, DECK.getDeckSize());
    }

    @Test
    public void DrawFiveCards(){
        ArrayList<ProgramCard> cards;
        cards = DECK.drawCards(5);
        assertEquals(5, cards.size());
    }

    @Test
    public void DeckContains79CardsAfterFiveDrawn(){
        DECK.drawCards(5);
        assertEquals(79, DECK.getDeckSize());
    }

    @Test
    public void FullDeckContains18MoveOneCards(){
        int count = 0;
        for (ProgramCard c : DECK.getAvailableCards()) {
            if (c.getValue() == Value.MOVE_ONE) {
                count++;
            }
        }
        assertEquals(18, count);
    }
}
