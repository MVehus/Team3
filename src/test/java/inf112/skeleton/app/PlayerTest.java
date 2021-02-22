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

}
