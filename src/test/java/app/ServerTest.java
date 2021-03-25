package app;

import network.Client;
import network.Network;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {

    @Test
    public void playerCanConnectToServer() {
        Network.startServer(32402);
        new Client("localhost", 32402);
        new Client("localhost", 32402);
        new Client("localhost", 32402);
        assertEquals(3, Network.getAllClientsOnServer().size());
    }

    @Test
    public void disconnectedPlayersAreRemovedFromClientList(){
        Network.startServer(32403);
        Network.makeNewClient("localhost", 32403);
        new Client("localhost", 32403);
        new Client("localhost", 32403);
        Network.disconnectClient();
        assertEquals(2, Network.getAllClientsOnServer().size());
    }
}