package dev.hafnerp.tcp;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private Integer PORT;

    private String HOST;

    private Socket socket;


    public TCPClient(Integer PORT, String HOST) {
        this.PORT = PORT;
        this.HOST = HOST;
    }

    public boolean connect() {
        try {
            socket = new Socket(HOST, PORT);
            return true;
        }
        catch (Exception ignore) {
            return false;
        }

    }

    public boolean send(String message) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(socket.getOutputStream(), true);

            InputStreamReader inputStreamReader;
            inputStreamReader = new InputStreamReader(socket.getInputStream());

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(inputStreamReader);

            writer.println(message);

            String line;
            while ((line =bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            System.out.println("Socket not connected!");
        }

        return true;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
