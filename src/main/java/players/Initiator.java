package players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * represents initiator player who will initiate the first message using socket connection
 * 
 * @author Amr Ramzy
 *
 */
public class Initiator extends Player {
	

	/**
	 * server socket time out in milliseconds
	 */
	private static final int SOCKET_TIME_OUT = 60000;

	
	/**
	 * @param port port number used to open socket connection
	 */
	public Initiator(int port) {
		super(port);
	}
	
	/**
	 * method is used to initialize Initiator player and send the first message 
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	protected void initPlayer() throws IOException {
		
		log.info("[{}] started.", Thread.currentThread().getName());
		socket = new Socket();
		socket.connect(new InetSocketAddress("127.0.0.1", port), SOCKET_TIME_OUT);
		log.info("[{}] socket connected on port {}.", Thread.currentThread().getName(), port);
		outputStream = socket.getOutputStream();
		printWriter = new PrintWriter(outputStream);

		inputStream = socket.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		sendInitialMessage();
	}

	protected void sendInitialMessage() {
		printWriter.println(numberOfSentMessages);
		log.info("[{}] sent message {}.", Thread.currentThread().getName(),numberOfSentMessages);
		numberOfSentMessages++;
		printWriter.flush();
	}

	/**
	 * method is used to finalize Initiator player and close open sockets and streams
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	protected void finalizePlayer() throws IOException {
		bufferedReader.close();
		printWriter.close();
		outputStream.close();
		inputStream.close();
		socket.close();
		log.info("[{}] ended.", Thread.currentThread().getName());
	}
	
	

}
