package multiplayer;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import inf112.skeleton.app.Game;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GameServer {

    private ServerSocket serverSocket;

    public GameServer(){
        System.out.println("Game Lobby");

        try {
            serverSocket = new ServerSocket(54698);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void acceptConnections(){
        System.out.println("Waiting for people to connect");
        try {
            while(true) {
                Socket socket = serverSocket.accept();

                System.out.println("Player has connected!");
                ServerSideConnection ssc = new ServerSideConnection(socket);
                Thread t = new Thread(ssc);
                t.start();
            }

        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

    private class ServerSideConnection implements Runnable {

        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        public ServerSideConnection(Socket socket){
            this.socket = socket;
            try {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e){
                System.out.println(e.toString());
            }
        }

        @Override
        public void run() {
            try {
                dos.writeBytes("Hello and welcome");
                dos.flush();
                while (true) {

                }
            } catch (IOException e){
                System.out.println(e.toString());
            }
        }
    }
}
