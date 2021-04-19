package app;

import com.badlogic.gdx.math.Interpolation;
import network.Client;
import network.Network;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {

    @Test
    public void playerCanConnectToServer() {
        Network.startServer(32402);
        try {
            new Client("localhost", 32402);
            new Client("localhost", 32402);
            new Client("localhost", 32402);
            assertEquals(3, Network.getAllClientsOnServer().size());
        } catch (Exception e) {
            System.out.println("Test failed with exception: "+ e.toString());
        }
    }

    @Test
    public void disconnectedPlayersAreRemovedFromClientList(){
        Network.startServer(32403);
        Network.makeNewClient("localhost", 32403);
        try {
            new Client("localhost", 32403);
            new Client("localhost", 32403);
        } catch (Exception e){
            System.out.println(e);
        }
        Network.disconnectClient();
        assertEquals(2, Network.getAllClientsOnServer().size());
    }
}