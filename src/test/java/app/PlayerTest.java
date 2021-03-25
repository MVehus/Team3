package app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import player.Player;
import projectCard.CardDeck;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Vector2 pos = new Vector2(1,6);
    Player player = new Player(1, "TestPlayer", pos);
    CardDeck deck = new CardDeck();

    @Test
    public void PlayerHasFullHealthAtStart(){
        assertEquals(3, player.getHealth());
    }

    @Test
    public void GetPosition(){
        Vector2 pos = new Vector2(1,6);
        assertEquals(pos, player.getPosition());
    }

    @Test
    public void StartDirectionIsRight(){
        assertEquals(Direction.RIGHT, player.getDirection());
    }

    @Test
    public void RespawnAtStartPosIfNoCheckpointRegistered(){
        Vector2 startPos = new Vector2(1,6);
        player.reset();
        assertEquals(startPos, player.getCheckPointPosition());
    }

    @Test
    public void RespawnAtCheckpoint(){
        Vector2 checkPointPos = new Vector2(10, 10);
        player.setPosition((int) checkPointPos.x, (int) checkPointPos.y);
        player.markPosAsCheckpoint();
        player.reset();
        assertEquals(checkPointPos, player.getCheckPointPosition());
    }

    @Test
    public void PlayerStartsWithNoDamageTokens(){
        assertEquals(0,player.getNumDamageTokens());
    }

    @Test
    public void PlayerGetsDamageTokenWhenPlayerTakesDamage(){
        player.takeDamage();
        assertEquals(1,player.getNumDamageTokens());
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
        assertEquals(0, player.getNumDamageTokens());
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
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(9, player.cards.size());
    }

    @Test
    public void PlayerGetsEightCardsWithOneDamageToken() {
        player.takeDamage();
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(8, player.cards.size());
    }

    @Test
    public void PlayerGetsSevenCardsWithTwoDamageTokens() {
        for(int i = 0; i < 2; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(7, player.cards.size());
    }

    @Test
    public void PlayerGetsSixCardsWithThreeDamageTokens() {
        for(int i = 0; i < 3; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(6, player.cards.size());
    }

    @Test
    public void PlayerGetsFiveCardsWithFourDamageTokens() {
        for(int i = 0; i < 4; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(5, player.cards.size());
    }

    @Test
    public void PlayerGetsFourCardsWithFiveDamageTokens() {
        for(int i = 0; i < 5; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(4, player.cards.size());
    }

    @Test
    public void PlayerGetsThreeCardsWithSixDamageTokens() {
        for(int i = 0; i < 6; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(3, player.cards.size());
    }

    @Test
    public void PlayerGetsTwoCardsWithSevenDamageTokens() {
        for(int i = 0; i < 7; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(2, player.cards.size());
    }

    @Test
    public void PlayerGetsOneCardWithEightDamageTokens() {
        for(int i = 0; i < 8; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(1, player.cards.size());
    }

    @Test
    public void PlayerGetsNoCardsWithNineDamageTokens() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        player.cards = deck.drawCards(9- player.getNumDamageTokens());
        assertEquals(0, player.cards.size());
    }

    @Test
    public void PlayerGetsOneRegisterLockedWithFiveDamageTokens() {
        for(int i = 0; i < 5; i++) {
            player.takeDamage();
        }
        assertEquals(1, player.numLockedProgramCards());
    }

    @Test
    public void PlayerGetsTwoRegistersLockedWithSixDamageTokens() {
        for(int i = 0; i < 6; i++) {
            player.takeDamage();
        }
        assertEquals(2, player.numLockedProgramCards());
    }

    @Test
    public void PlayerGetsThreeRegistersLockedWithSevenDamageTokens() {
        for(int i = 0; i < 7; i++) {
            player.takeDamage();
        }
        assertEquals(3, player.numLockedProgramCards());
    }

    @Test
    public void PlayerGetsFourRegistersLockedWithEightDamageTokens() {
        for(int i = 0; i < 8; i++) {
            player.takeDamage();
        }
        assertEquals(4, player.numLockedProgramCards());
    }

    @Test
    public void PlayerGetsFiveRegistersLockedWithNineDamageTokens() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        assertEquals(5, player.numLockedProgramCards());
    }

    @Test
    public void PowerDownRepairsAllDamage() {
        for(int i = 0; i < 9; i++) {
            player.takeDamage();
        }
        player.powerDownComplete();
        assertEquals(0, player.getNumDamageTokens());
    }
}
