package streams;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) throws IOException {
		try (	
			ServerSocket serverSocket = new ServerSocket(1024);
			Socket clientSocket = serverSocket.accept()) {
				
		}
	}
}
