package app;

import com.badlogic.gdx.Net;
import network.Network;
import org.junit.jupiter.api.Test;
import projectCard.ProgramCard;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ServerClientCommunicationTest {

    @Test
    void ServerDealsCardToPlayer() {
        Network.startServer(32401);
        Network.makeNewClient("localhost", 32401);

        Network.dealCardsToPlayers();
        ArrayList<ProgramCard> hand = Network.getCurrentProgramCards();

        assertEquals(9, hand.size());
    }
}
