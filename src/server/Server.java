package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import client.BasePacket;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final Gson gson;

    public Server() {
        gson = new Gson();
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BasePacket handshakePacket = gson.fromJson(in.readLine(), BasePacket.class);

        if (handshakePacket.type == BasePacket.PacketType.HandShake) {
            sendPacket(new BasePacket(BasePacket.PacketType.Acknowledge));
        } else {
            BasePacket responsePacket = new BasePacket(BasePacket.PacketType.Error);
            responsePacket.errorType = BasePacket.ErrorType.UnrecognizedGreeting;
            sendPacket(responsePacket);
            stop();
            return;
        }

        lifeCycle();
    }

    public void lifeCycle() {
        while (true) {
            try {
                BasePacket packet = stringToPacket(in.readLine());
                if (packet.type == BasePacket.PacketType.Disconnect) {
                    stop();
                    break;
                }

                digestPacket(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidPacket e) {
                BasePacket packet = new BasePacket(BasePacket.PacketType.Error);
                packet.errorType = BasePacket.ErrorType.InvalidPacketData;
                sendPacket(packet);
            }
        }
    }

    public void digestPacket(BasePacket packet) {
        switch (packet.type) {

        }
    }

    public BasePacket stringToPacket(String rawData) throws InvalidPacket {
        try {
            return gson.fromJson(rawData, BasePacket.class);
        } catch (JsonSyntaxException jse) {
            throw new InvalidPacket();
        }
    }

    public BasePacket sendPacket(BasePacket packet) {
        out.println(gson.toJson(packet));
        return packet;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(6666);
    }

    private class InvalidPacket extends Exception {
    }
}