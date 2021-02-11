package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;
import projectCard.Card;
import projectCard.CardDeck;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CardDeckTest {

    private CardDeck DECK = new CardDeck();

    @Test
    public void FullDeckContains84Cards(){
        assertEquals(84, DECK.getTotalCards());
    }

    @Test
    public void DrawFiveCards(){
        ArrayList<Card> cards = new ArrayList<>();
        cards = DECK.drawCards(5);
        assertEquals(5, cards.size());
    }

    @Test
    public void DeckContains79CardsAfterFiveDrawn(){
        DECK.drawCards(5);
        assertEquals(79, DECK.getTotalCards());
    }

    @Test
    //TODO
    public void FullDeckCon tains18MoveOneCards(){
        int count = 0;
        for (Card c : DECK.getDeck()) {
            // TODO
        }
    }


}
