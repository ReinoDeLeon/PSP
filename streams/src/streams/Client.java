package streams;

public class Client {
	
	public static void main(String[] args) {
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
	}
}
