package multiplayer;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Player {

    String name;
    ClientSideConnection csc;

    public Player(String s){
        name = s;
    }

    public void connectToServer(){
        csc = new ClientSideConnection();
    }

    public static void main(String[] args) {
        Player player = new Player("Mathias");
        player.connectToServer();
    }

    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ClientSideConnection(){
            System.out.println("Client");
            try {
                socket = new Socket("localhost", 54698);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                dos.write(name.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e){
                System.out.println(e.toString());
            }
        }
    }
}


