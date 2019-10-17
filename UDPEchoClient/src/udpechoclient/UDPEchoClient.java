/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpechoclient;

import java.io.*;
import java.net.*;

public class UDPEchoClient
{
private static InetAddress host;
private static final int PORT = 1234;
private static DatagramSocket dgramSocket;
private static DatagramPacket inPacket, outPacket;
private static byte[] buffer;

public static void main(String[] args) {
try {
    host = InetAddress.getLocalHost();
} catch(UnknownHostException e) {
    System.out.println("Host ID not found!");
    System.exit(1);
    }
run();
}

private static void run() {
    try {
        dgramSocket = new DatagramSocket();		
	
	BufferedReader userEntry = new BufferedReader(
                                    new InputStreamReader(System.in));
	String message=null;
        String response=null;
	do {
            System.out.print("Enter message: ");
            message = userEntry.readLine();
            
		outPacket = new DatagramPacket(message.getBytes(),message.length(), host, PORT);    
		dgramSocket.send(outPacket);	
		
                buffer = new byte[256];     
		inPacket = new DatagramPacket(buffer, buffer.length); 	
		dgramSocket.receive(inPacket);	
		response = new String(inPacket.getData(), 0, inPacket.getLength());	
		System.out.println("\n<SERVER> " + response);
            
	}while (!message.equals("STOP"));
        
    } catch(IOException e) {
        e.printStackTrace();
        } finally {
            System.out.println("\n Closing connection... ");
            dgramSocket.close();	
        }
}
}
