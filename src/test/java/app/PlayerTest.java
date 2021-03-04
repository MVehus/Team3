package app;

import org.junit.Test;
import player.Player;
import projectCard.CardDeck;


import static org.junit.Assert.assertEquals;

public class PlayerTest {

    Player player = new Player("TestPlayer");
    CardDeck deck = new CardDeck();

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
    public void PlayerLosesLifeTokenWhenReachingTenDamageTokens(){
        for(int i = 0; i < 10; i++) {
            player.takeDamage();
        }
        assertEquals(2, player.getHealth());
    }

    @Test
    public void PlayerResetDamageTokensWhenLosingLifeToken() {
        for(int i = 0; i < 10; i++) {
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
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(9, player.playercards.size());
    }

    @Test
    public void PlayerGetsEightCardsWithOneDamageToken() {
        player.takeDamage();
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(8, player.playercards.size());
    }

    @Test
    public void PlayerGetsSevenCardsWithTwoDamageTokens() {
        for(int i = 0; i < 2; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(7, player.playercards.size());
    }

    @Test
    public void PlayerGetsSixCardsWithThreeDamageTokens() {
        for(int i = 0; i < 3; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(6, player.playercards.size());
    }

    @Test
    public void PlayerGetsFiveCardsWithFourDamageTokens() {
        for(int i = 0; i < 4; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(5, player.playercards.size());
    }

    @Test
    public void PlayerGetsFourCardsWithFiveDamageTokens() {
        for(int i = 0; i < 5; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(4, player.playercards.size());
    }

    @Test
    public void PlayerGetsThreeCardsWithSixDamageTokens() {
        for(int i = 0; i < 6; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(3, player.playercards.size());
    }

    @Test
    public void PlayerGetsTwoCardsWithSevenDamageTokens() {
        for(int i = 0; i < 7; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(2, player.playercards.size());
    }

    @Test
    public void PlayerGetsOneCardWithEightDamageTokens() {
        for(int i = 0; i < 8; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(1, player.playercards.size());
    }

    @Test
    public void PlayerGetsNoCardsWithNineDamageTokens() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        player.playercards = deck.drawCards(9- player.getDamageTokens());
        assertEquals(0, player.playercards.size());
    }

    @Test
    public void PlayerGetsOneRegisterLockedWithFiveDamageTokens() {
        for(int i = 0; i < 5; i++) {
            player.takeDamage();
        }
        assertEquals(1, player.getLockedPositions());
    }

    @Test
    public void PlayerGetsTwoRegistersLockedWithSixDamageTokens() {
        for(int i = 0; i < 6; i++) {
            player.takeDamage();
        }
        assertEquals(2, player.getLockedPositions());
    }

    @Test
    public void PlayerGetsThreeRegistersLockedWithSevenDamageTokens() {
        for(int i = 0; i < 7; i++) {
            player.takeDamage();
        }
        assertEquals(3, player.getLockedPositions());
    }

    @Test
    public void PlayerGetsFourRegistersLockedWithEightDamageTokens() {
        for(int i = 0; i < 8; i++) {
            player.takeDamage();
        }
        assertEquals(4, player.getLockedPositions());
    }

    @Test
    public void PlayerGetsFiveRegistersLockedWithNineDamageTokens() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        assertEquals(5, player.getLockedPositions());
    }
}
