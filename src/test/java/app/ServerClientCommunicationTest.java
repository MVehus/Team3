package app;

import network.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectCard.ProgramCard;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ServerClientCommunicationTest {

    @BeforeEach
    void init(){
        Network.startServer(32401);
        Network.makeNewClient("localhost", 32401);
    }

    @AfterEach
    void cleanup(){
        Network.shutdownServer();
    }

    @Test
    void ServerDealsCardToPlayer() {
        Network.dealCardsToPlayers();
        ArrayList<ProgramCard> hand = Network.getCurrentProgramCards();

        assertEquals(9, hand.size());
    }

    @Test
    void ClientKnowsWhenGameStarts(){
        Network.startGame();

        await();

        assertTrue(Network.gameStarted());
    }

    /**
     * Method used to compensate for the time delay in server/client communication
     */
    void await(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){

        }
    }
}
