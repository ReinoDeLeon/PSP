package stream_threads;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Server {
	public static final int MAX_PORT_NUMBER = 65535;
	public static final int MIN_PORT_NUMBER = 1;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length!=1) { // No args
			System.err.println("Usage: java Sever <port number>");
			System.exit(1);
		}
		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e) { // Args not a number
			System.err.println("Error: java Sever <port number> must be a number");
			System.exit(1);
		}
		if (portNumber< MIN_PORT_NUMBER || portNumber > MAX_PORT_NUMBER) { // Port number invalid
			System.err.printf("Error: java Sever <port number> value must be an integer between %d and %d\n", MIN_PORT_NUMBER, MAX_PORT_NUMBER);
			System.exit(1);
		}

		Observable observable = new Observable();
		try (
				ServerSocket serverSocket = new ServerSocket(portNumber);
				){
			while (true) {
				Socket clientSocket = serverSocket.accept(); 
				System.out.printf("%s connected\n", clientSocket.toString());
				PeerConnection peerConnection = new PeerConnection(clientSocket, observable);
				peerConnection.start();

			}
		}catch (BindException e) { // Port in use
				System.err.printf("Error: port %d is already in use\n", portNumber);
				System.exit(1);
			} 
		}
	}

