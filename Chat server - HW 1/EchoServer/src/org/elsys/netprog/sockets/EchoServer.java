package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer implements Runnable {
	private int id;
	private BufferedReader in;
	private static int userCount = 0;
	private static List <PrintWriter> outs = new ArrayList<>();
	public EchoServer(int id, BufferedReader in) {
		this.id = id;
		this.in = in;
	}
	
	public void run() { 
        try {
 		    String inputLine;
 		    boolean disconnected = false;
 		    while ((inputLine = in.readLine()) != null) {
 		    	if (inputLine.equals("exit")) {
 		    		inputLine = "disconnected.";
 		    		disconnected  = true;
 		    	} 
 		    	System.out.println("Id: " + id + "outs size : " + outs.size());
 		    	for(int i = 0; i < outs.size(); i++) {
  		        	if(i != id) {
  		        		outs.get(i).println("User " + id + ": " + inputLine);
  		        	}
 		    	}
 		        System.out.println(inputLine);
 		        if(disconnected)
 		        	break;
 		    }
 		    userCount--;
  
        } 
        catch (Exception e) { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
  
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(10001);
			do {
				Socket clientSocket = serverSocket.accept();
				BufferedReader in = new BufferedReader(
		 		        new InputStreamReader(clientSocket.getInputStream()));
				outs.add( new PrintWriter(clientSocket.getOutputStream(), true));
				Thread t = new Thread(new EchoServer(userCount++, in));
				t.start();
				System.out.println("client connected from " + clientSocket.getInetAddress() + " with id: " + userCount);
			} while(userCount > 0);
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			
			System.out.println("Server closed");
		}
	}

}
