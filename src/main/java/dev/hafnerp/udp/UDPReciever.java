package dev.hafnerp.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Timestamp;

public class UDPReciever implements Runnable {

    private DatagramSocket serverSocketD = null;

    public UDPReciever(Integer PORT) throws SocketException {
        this.serverSocketD = new DatagramSocket(PORT);
    }

    @Override
    public void run() {

        byte []receiveData = new byte[1024];
        DatagramPacket receivedPacket;


        while (true) {
            receivedPacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocketD.receive(receivedPacket);

                InetAddress IPAddress = receivedPacket.getAddress();
                Integer port = receivedPacket.getPort();
                String clientMessage = new String(receivedPacket.getData(),0,receivedPacket.getLength());

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                System.out.println("UDP - [" + timestamp.toString() + " ,IP: " + IPAddress + " ,Port: " + port +"]  " + clientMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close() {
        serverSocketD.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
