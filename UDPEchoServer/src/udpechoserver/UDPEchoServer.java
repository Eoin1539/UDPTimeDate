/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpechoserver;
import java.io.*;
import java.net.*;

public class UDPEchoServer
{
    private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) 
    {
	System.out.println("Opening port...\n");
	try 
        {
            dgramSocket = new DatagramSocket(PORT);//Step 1.
	} 
        catch(SocketException e) 
        {
            System.out.println("Unable to attach to port!");
	    System.exit(1);
	}
	run();
    }

    private static void run()
    {
	try 
        {
            String messageIn, messageOut="";
            int numMessages = 0;

            do 
            {
                buffer = new byte[256]; 		
                inPacket = new DatagramPacket(buffer, buffer.length); 
                dgramSocket.receive(inPacket);	

                InetAddress clientAddress = inPacket.getAddress();	
                int clientPort = inPacket.getPort();		

                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());	
                if(messageIn.equalsIgnoreCase("time")){
                    messageOut = ("The time is "+java.time.LocalTime.now().toString());
                    System.out.println("Command received "+messageIn);
                System.out.println("Sending "+messageOut+" back to the client");
                }
                
                else if(messageIn.equalsIgnoreCase("date")){
                    messageOut = ("The date is "+java.time.LocalDate.now().toString());
                    System.out.println("Command received "+messageIn);
                    System.out.println("Sending "+messageOut+" back to the client");
                }
                
                else{
                    messageOut=("Error, incorrect syntax received, try again.");
                    System.out.println("Command received "+messageIn);
                    System.out.println("Sending "+messageOut+" back to the client");
                }
                
                
               
                
                outPacket = new DatagramPacket(messageOut.getBytes(),
                                         messageOut.length(),
                                         clientAddress,	
                                         clientPort);		
                dgramSocket.send(outPacket);	
            }while (true);
        } 
        catch(IOException e)
        {
            e.printStackTrace();
	} 
        finally {		//If exception thrown, close connection.
            System.out.println("\n Closing connection... ");
            dgramSocket.close();				//Step 9.
	}
    }
}
