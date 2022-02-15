package stream_threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class PeerConnection extends Thread implements Observer{

	private static final String PRIVATE_MESSAGE = "*Private Message*";
	private static final String PUBLIC_MESSAGE = "*Public Message*";
	private static final String COMMAND_PREFIX = "/";
	public String clientNickname = "Unkown";
	private Socket clientSocket;
	private Channel observable;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private String privateNickname;

	public PeerConnection(Socket _clientSocket, Observable _observable) throws IOException {
		clientSocket = _clientSocket;
		observable = (Channel) _observable;
		observable.addObserver(this);
		socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
		socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	}

	@Override
	public void run() {
		try {

			String line;
			while ((line = socketIn.readLine()) != null) { // We read from client, null --> client closed connection
				
				if (line.startsWith(COMMAND_PREFIX)) {
					//String commandGetter = line.substring(1, line.indexOf(" "));
					String commandGetter = line.substring(1);
					if (commandGetter.startsWith("nick")) {
						//String nickValue = line.substring(line.indexOf(" "));
						String nickname = commandGetter.substring("nick".length()+1);
						if (nickname.length()>0) {
							clientNickname = nickname;
						}
					}
					if (commandGetter.startsWith("priv")) {
						//String nickValue = line.substring(line.indexOf(" "));
						privateNickname = commandGetter.substring("priv".length()+1, commandGetter.lastIndexOf(COMMAND_PREFIX));
						String message = commandGetter.substring(commandGetter.lastIndexOf(COMMAND_PREFIX)+1);
						observable.notifyObservers(String.format("%s to %s from [%s]: %s", PRIVATE_MESSAGE ,privateNickname, clientNickname, message));
					}
				} 
				
				else {
					observable.notifyObservers(String.format("%s[%s]: %s", PUBLIC_MESSAGE ,clientNickname, line));
				}
				
				
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			socketOut.close();
			try {
				socketIn.close();
				observable.deleteObserver(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.toString().startsWith(PRIVATE_MESSAGE)) { //Comprobamos que la frase comience por mensaje privado indicando que de hecho lo es
			String userAndMessage = arg.toString().substring(PRIVATE_MESSAGE.length()); //Conjunto de usuario objetivo y mensaje a mostrar
			String user = userAndMessage.substring(4, userAndMessage.indexOf(" from")); //Guardamos el usuario objetivo
			String message = userAndMessage.substring(userAndMessage.indexOf(" from ")); //Guardamos el mensaje a mostrar con el emisor y el mensaje
			if(clientNickname.equals(user)) {
			socketOut.println(String.format("%s%s", PRIVATE_MESSAGE, message));
		}
		} else if(arg.toString().startsWith(PUBLIC_MESSAGE)){
			socketOut.println(arg.toString());
		}
	}
}
