package stream_threads;

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
		if (args.length < 2) { // No args
			System.err.println("Usage: java Client <ip address> <port number>");
			System.exit(1);
		}

		InetAddress host = null;
		try {
			host = InetAddress.getByName(args[0]);
			if (!host.isReachable(10)) {
				System.err.printf("Usage: can't reach <ip address> %s\n", args[0]);
				System.exit(1);
			}
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			System.err.printf("Usage: <ip address> %s is invalid\n", args[0]);
			System.exit(1);
		}

		int portNumber = 0;
		try {
			portNumber = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e) { // Args not a number
			System.err.println("Error: java Sever <port number> must be a number");
			System.exit(1);
		}
		if (portNumber< Server.MIN_PORT_NUMBER || portNumber > Server.MAX_PORT_NUMBER) { // Port number invalid
			System.err.printf("Error: java Sever <port number> value must be an integer between %d and %d\n", Server.MIN_PORT_NUMBER, Server.MAX_PORT_NUMBER);
			System.exit(1);
		}

		//		try (
		Socket socket = new Socket(host, portNumber);
			//		}catch (BindException e) { // Port in use
			//			System.err.printf("Error: port %d is already in use\n", portNumber);
			//			System.exit(1);
			//		}

			Thread keyboardThread = new Thread(new Runnable() {

				@Override
				public void run() {
					BufferedReader standardIn = null;
					PrintWriter socketOut = null; //Null so the finally block doesn't launch exception
					try {
						standardIn = new BufferedReader((new InputStreamReader(System.in)));
						socketOut = new PrintWriter(socket.getOutputStream(), true); // True so it sends the data automatically 	
						String line;
						while ((line = standardIn.readLine()) != null) {
							socketOut.println(line);
						}
					} catch (IOException e) {


					} finally {
						try {
							standardIn.close();
							socketOut.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});

			Thread networkThread = new Thread(new Runnable() {

				@Override
				public void run() {
					BufferedReader socketIn = null;
					try {
						socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String line;
						while ((line = socketIn.readLine()) != null) {
							System.out.println(line);
						}
					} 
					catch (IOException e) {
					} 
					finally {
						try {
							socketIn.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});	
			
			keyboardThread.start();
			networkThread.start();
			try {
				keyboardThread.join();
				networkThread.join();
			} catch (InterruptedException e) {
				
			}
		}
	}
	
