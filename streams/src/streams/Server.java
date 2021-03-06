package streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int MAX_PORT_NUMBER = 65535;
	public static final int MIN_PORT_NUMBER = 1;
	public static void main(String[] args) throws IOException {
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
		try (	
				ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream(), true); // True so it sends the data automatically 				
				) {
			String line;
			String lineReverse="";
				while ((line = socketIn.readLine()) != null) { // We read from client, null --> client closed connection
					for (int i = line.length()-1; i >= 0; i--) {
						lineReverse += line.charAt(i);
					} 
					socketOut.println(lineReverse);
					lineReverse = "";
				}
				
			}
		catch (BindException e) { // Port in use
			System.err.printf("Error: port %d is already in use\n", portNumber);
			System.exit(1);
		}
	}
}
