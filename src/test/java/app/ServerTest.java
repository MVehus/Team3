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
}