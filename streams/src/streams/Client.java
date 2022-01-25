package streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
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
		if (portNumber< Server.MIN_PORT_NUMBER || portNumber > Server.MAX_PORT_NUMBER) { // Port number invalid
			System.err.printf("Error: java Sever <port number> value must be an integer between %d and %d\n", Server.MIN_PORT_NUMBER, Server.MAX_PORT_NUMBER);
			System.exit(1);
		}
		InetAddress localHost = InetAddress.getLocalHost();
		try (
				Socket socket = new Socket(localHost, portNumber);
				BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); // True so it sends the data automatically 	
				BufferedReader standardIn = new BufferedReader((new InputStreamReader(System.in)));
				
				){
			String line;
			while ((line = standardIn.readLine()) != null) {
				socketOut.println(line);
				System.out.println(socketIn.readLine());;
			}
			
		}catch (BindException e) { // Port in use
			System.err.printf("Error: port %d is already in use\n", portNumber);
			System.exit(1);
		}
	}
}
