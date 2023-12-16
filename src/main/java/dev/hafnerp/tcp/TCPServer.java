package dev.hafnerp.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

public class TCPServer  implements  Runnable {

    private Integer PORT;

    private ServerSocket serverSocket;

    public TCPServer(Integer PORT) {
        this.PORT = PORT;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket;
                socket = serverSocket.accept();

                InputStream inputStream;
                inputStream = socket.getInputStream();

                InputStreamReader inputStreamReader;
                inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder;
                stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                Timestamp timestamp;
                timestamp = new Timestamp(System.currentTimeMillis());

                System.out.println("TCP - [" + timestamp.toString() + " ,IP: " + socket.getInetAddress() + " ,Port: " + socket.getPort() +"]  " + stringBuilder);

                OutputStream outputStream;
                outputStream = socket.getOutputStream();

                PrintWriter printWriter;
                printWriter = new PrintWriter(outputStream);

                printWriter.println("Server: HELLO WORLD!");

                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
