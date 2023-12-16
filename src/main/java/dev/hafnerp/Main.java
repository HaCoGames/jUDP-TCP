package dev.hafnerp;

import dev.hafnerp.tcp.TCPClient;
import dev.hafnerp.tcp.TCPServer;
import dev.hafnerp.udp.UDPReciever;
import dev.hafnerp.udp.UDPSender;

import java.io.*;
import java.util.Properties;

public class Main {

    private static Integer PORT;

    private static String HOSTNAME;

    public static void main(String[] args) throws IOException, InterruptedException {
        String propertyFileName = "config.properties";
        Properties properties;
        properties = new Properties();

        try {
            properties.load(new FileReader(propertyFileName));
        }
        catch (Exception ignore) {
            properties.setProperty("PORT", "2020");
            properties.setProperty("HOST", "localhost");

            properties.store(new BufferedOutputStream(
                            new FileOutputStream(propertyFileName)),
                    "This is the configuration file for UDP server and client"
            );
        }

        PORT = Integer.parseInt(properties.getProperty("PORT"));
        HOSTNAME = properties.getProperty("HOST");

        //------------------------------------------------------------------------------------------------->
        //UDP - Testing
        UDPReciever udpReciever;
        udpReciever = new UDPReciever(PORT);

        Thread receiver;
        receiver = new Thread(udpReciever);
        receiver.start();

        Thread.sleep(20);

        UDPSender udpSender;
        udpSender = new UDPSender(PORT, HOSTNAME);

        udpSender.send("Hello World!");
        udpSender.send(
                "Hallo Du!"
        );

        receiver.interrupt();

        //------------------------------------------------------------------------------------------------->
        //TCP - Testing
        TCPServer tcpServer;
        tcpServer = new TCPServer(PORT);

        Thread tcpThread;
        tcpThread = new Thread(tcpServer);
        tcpThread.start();

        TCPClient tcpClient;
        tcpClient = new TCPClient(PORT, HOSTNAME);

        Thread.sleep(200);

        tcpClient.connect();
        tcpClient.send("Joe \n mana");
        tcpClient.close();

        tcpThread.interrupt();
    }
}