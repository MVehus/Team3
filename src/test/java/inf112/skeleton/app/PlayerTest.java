package inf112.skeleton.app;

import org.junit.Test;
import player.Player;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    Player player = new Player();


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
        //TODO
    }

    @Test
    public void PlayerGetsEightCardsWithOneDamageToken() {
        //TODO
    }

    @Test
    public void PlayerGetsSevenCardsWithTwoDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsSixCardsWithThreeDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsFiveCardsWithFourDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsFourCardsWithFiveDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsThreeCardsWithSixDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsTwoCardsWithSevenDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsOneCardWithEightDamageTokens() {
        //TODO
    }

    @Test
    public void PlayerGetsNoCardsWithNineDamageTokens() {
        //TODO
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
