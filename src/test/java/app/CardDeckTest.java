package app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import player.Player;
import projectCard.ProgramCard;
import projectCard.CardDeck;
import projectCard.Value;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CardDeckTest {

    private CardDeck DECK = new CardDeck();
    private List<Player> players = new ArrayList<>();

    @Test
    public void FullDeckContains84Cards(){
        assertEquals(84, DECK.getDeckSize());
    }

    @Test
    public void DrawFiveCards(){
        ArrayList<ProgramCard> cards = new ArrayList<>();
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

    @Test
    public void RestockDeck(){
        ArrayList<ProgramCard> cards = new ArrayList<>();
        DECK.drawCards(30);
        DECK.restock();
        assertEquals(84, DECK.getDeckSize());
        assertEquals(0, DECK.getUsedCardsSize());
    }

    @Test
    public void PlayerInPowerDownGetsNoCard() {
        Player player1 = new Player(1, "test1", new Vector2(1,1));
        players.add(player1);
        players.get(0).setPowerDown();
        DECK.dealCards(players);
        assertEquals(84, DECK.getAvailableCards().size());
    }

    @Test
    public void FiveUndamagedPlayersDraw45Cards() {
        Player player1 = new Player(1, "test1", new Vector2(1,1));
        Player player2 = new Player(2, "test2", new Vector2(2,2));
        Player player3 = new Player(3, "test3", new Vector2(3,3));
        Player player4 = new Player(4, "test4", new Vector2(4,4));
        Player player5 = new Player(5, "test5", new Vector2(5,5));
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        DECK.dealCards(players);
        assertEquals(39, DECK.getAvailableCards().size());
        assertEquals(45, DECK.getUsedCardsSize());
    }

}
