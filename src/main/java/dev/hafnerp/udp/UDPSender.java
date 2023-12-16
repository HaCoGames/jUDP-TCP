package dev.hafnerp.udp;

import java.net.*;
import java.util.Scanner;

public class UDPSender {

    private Integer PORT;

    private String hostname;

    public UDPSender(Integer PORT) {
        this.PORT = PORT;
        this.hostname = "localhost";
    }

    public UDPSender(Integer PORT, String hostname) {
        this.PORT = PORT;
        this.hostname = hostname;
    }

    public void send(String message) {
        Scanner scanner;
        scanner = new Scanner(System.in);
        byte []inputBytes;
        if (message == null)
             inputBytes = scanner.nextLine().getBytes();
        else inputBytes = message.getBytes();

        try {
            InetAddress address;
            address = InetAddress.getByName("localhost");

            DatagramPacket datagramPacket;
            datagramPacket = new DatagramPacket(
                    inputBytes,
                    inputBytes.length,
                    address,
                    2020
            );

            DatagramSocket datagramSocket;
            datagramSocket = new DatagramSocket();

            datagramSocket.send(datagramPacket);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
