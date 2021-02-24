package inf112.skeleton.app;

import org.junit.Test;
import player.Player;
import projectCard.Card;
import projectCard.CardDeck;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    Player player = new Player();
    CardDeck deck = new CardDeck();
    ArrayList<Card> playercards = new ArrayList<Card>();


    @Test
    public void PlayerHasFullHealthAtStart(){
        assertEquals(3, player.getHealth());
    }

    @Test
    public void PlayerStartsWithNoDamageTokens(){
        assertEquals(0,player.getDamageTokens());
    }


    @Test
    public void PlayerGetsDamageTokenWhenPlayerTakesDamage(){
        player.takeDamage();
        assertEquals(1,player.getDamageTokens());
    }

    @Test
    public void PlayerLosesLifeTokenWhenReachingNineDamageTokens(){
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        assertEquals(2, player.getHealth());
    }

    @Test
    public void PlayerResetDamageTokensWhenLosingLifeToken() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        assertEquals(0, player.getDamageTokens());
    }

    @Test
    public void PlayerCanLoseAllLifeTokens() {
        for(int i = 0; i < 3; i++) {
            player.loseLifeToken();
        }
        assertEquals(0,player.getHealth());
    }

    @Test
    public void PlayerGetsNineCardsWithNoDamageTokens() {
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 9);
    }

    @Test
    public void PlayerGetsEightCardsWithOneDamageToken() {
        player.takeDamage();
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 8);
    }

    @Test
    public void PlayerGetsSevenCardsWithTwoDamageTokens() {
        for(int i = 0; i < 2; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 7);
    }

    @Test
    public void PlayerGetsSixCardsWithThreeDamageTokens() {
        for(int i = 0; i < 3; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 6);
    }

    @Test
    public void PlayerGetsFiveCardsWithFourDamageTokens() {
        for(int i = 0; i < 4; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 5);
    }

    @Test
    public void PlayerGetsFourCardsWithFiveDamageTokens() {
        for(int i = 0; i < 5; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 4);
    }

    @Test
    public void PlayerGetsThreeCardsWithSixDamageTokens() {
        for(int i = 0; i < 6; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 3);
    }

    @Test
    public void PlayerGetsTwoCardsWithSevenDamageTokens() {
        for(int i = 0; i < 7; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 2);
    }

    @Test
    public void PlayerGetsOneCardWithEightDamageTokens() {
        for(int i = 0; i < 8; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 1);
    }

    @Test
    public void PlayerGetsNoCardsWithNineDamageTokens() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(playercards.size(), 9);
    }

    @Test
    public void PlayerGetsRegisterFiveLockedWithFiveDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsRegisterFourLockedWithSixDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsRegisterThreeLockedWithSevenDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsRegisterTwoLockedWithEightDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsRegisterOneLockedWithNineDamageTokens() {
        //TODO
    }
}
