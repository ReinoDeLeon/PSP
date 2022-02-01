package stream_threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PeerConnection extends Thread {

	private Socket clientSocket;

	public PeerConnection(Socket _clientSocket) {
		clientSocket = _clientSocket;
	}

	@Override
	public void run() {
		try(
				BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream(), true); // True so it sends the data automatically 				
				) {

			String line;
			while ((line = socketIn.readLine()) != null) { // We read from client, null --> client closed connection
				socketOut.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	

	}
