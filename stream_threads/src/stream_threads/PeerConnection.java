package stream_threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class PeerConnection extends Thread implements Observer{



	private Socket clientSocket;
	private Observable observable;
	private PrintWriter socketOut;
	private BufferedReader socketIn;

	public PeerConnection(Socket _clientSocket, Observable _observable) throws IOException {
		clientSocket = _clientSocket;
		observable = _observable;
		observable.addObserver(this);
		socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
		socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	}

	@Override
	public void run() {
		try {

			String line;
			while ((line = socketIn.readLine()) != null) { // We read from client, null --> client closed connection
				observable.notifyObservers(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			socketOut.close();
			try {
				socketIn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		socketOut.write(arg.toString());
	}
}
