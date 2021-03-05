package app;

import network.Network;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {

    @Test
    public void playerCanConnectToServer(){
        Network.startServer(32401);
        Network.makeNewClient("localhost", 32401);
        assertEquals(1, Network.getAllClientsOnServer().size());
    }

    @Test
    public void disconnectedPlayersAreRemovedFromClientList(){
        Network.startServer(32401);
        Network.makeNewClient("localhost", 32401);
        Network.disconnectClient();
        assertEquals(0, Network.getAllClientsOnServer().size());
    }
}