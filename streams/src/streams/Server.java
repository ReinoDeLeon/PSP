package streams;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int MAX_PORT_NUMBER = 65535;
	private static final int MIN_PORT_NUMBER = 1;
	public static void main(String[] args) throws IOException {
		if (args.length!=1) {
			System.err.println("Usage: java Sever <port number>");
			System.exit(1);
		}
		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e) {
			System.err.println("Error: java Sever <port number> must be a number");
			System.exit(1);
		}
		if (portNumber< MIN_PORT_NUMBER || portNumber > MAX_PORT_NUMBER) {
			System.err.printf("Error: java Sever <port number> value must be an integer between %d and %d\n", MIN_PORT_NUMBER, MAX_PORT_NUMBER);
			System.exit(1);
		}
		try (	
				ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				) {
			}
	}
}
