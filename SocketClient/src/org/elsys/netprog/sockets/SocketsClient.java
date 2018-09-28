package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketsClient implements Runnable {
	private static boolean connected;
	private BufferedReader in;
	public SocketsClient(BufferedReader in) {
		this.in = in;
	}
	
	public void run() { 
		while(connected) {
			try {
				System.out.println(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Socket echoSocket = null;
		try {
			    echoSocket = new Socket("localhost", 10001);
			    PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			            new InputStreamReader(echoSocket.getInputStream()));
			    BufferedReader stdIn = new BufferedReader(
			            new InputStreamReader(System.in));
			    
			    String userInput;
			    connected = true;
			    Thread t = new Thread(new SocketsClient(in));
				t.start();
			    while ((userInput = stdIn.readLine()) != null) {
			        out.println(userInput);
			    }
			    connected = false;
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (echoSocket != null && !echoSocket.isClosed()) {
				echoSocket.close();
			}
		}
	}

}
