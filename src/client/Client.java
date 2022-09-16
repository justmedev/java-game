package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.*;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final Gson gson;

    public Client() {
        gson = new Gson();
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public BasePacket sendPacket(BasePacket packet) throws IOException {
        out.println(gson.toJson(packet));
        return gson.fromJson(in.readLine(), BasePacket.class);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
