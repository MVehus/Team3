package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class Client {

    public Client(String IpAddress, int port) {
        com.esotericsoftware.kryonet.Client client = new com.esotericsoftware.kryonet.Client();
        client.start();
        try {
            client.connect(5000, IpAddress, port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String response = (String) object;
                    System.out.println(response);
                }
            }
        });
    }
}
