package players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * represents a single player who can send and receive messages using socket connection
 *  
 * @author Amr Ramzy
 *  
 */
public class Player implements Runnable {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * server socket time out in milliseconds
	 */
	private static final int SERVER_SOCKET_TIME_OUT = 180000;

	/**
	 * default port number
	 */
	protected int port = 9999;

	/**
	 * server socket
	 */
	protected ServerSocket serverSocket;

	/**
	 * socket object
	 */
	protected Socket socket;

	/**
	 * outputStream used to sent data throw socket connection
	 */
	protected OutputStream outputStream;

	/**
	 * inputStream used to get data from socket connection
	 */
	protected InputStream inputStream;

	/**
	 * printWriter used to write data throw socket connection
	 */
	protected PrintWriter printWriter;

	/**
	 * bufferedReader used to read data from socket connection
	 */
	protected BufferedReader bufferedReader;

	/**
	 * number of messages sent
	 */
	protected int numberOfSentMessages = 0;

	/**
	 * number of messages received
	 */
	protected int numberOfReceivedMessages = 0;

	/**
	 * @param port port number used to open socket connection
	 */
	public Player(int port) {
		this.port = port;
	}

	/**
	 * method is used to initialize player and create socket
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	protected void initPlayer() throws IOException {

		log.info("[{}] started.", Thread.currentThread().getName());
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(SERVER_SOCKET_TIME_OUT);
		log.info("[{}] listening on port {}.", Thread.currentThread().getName(), port);
		socket = serverSocket.accept();
		log.info("[{}] socket connected on port {}.", Thread.currentThread().getName(), port);
		outputStream = socket.getOutputStream();
		printWriter = new PrintWriter(outputStream);
		inputStream = socket.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

	}

	/**
	 * code to be run when the thread starts send and receive messages until player
	 * sent MAX_NUMBER_SENT_MESSAGES and received MAX_NUMBER_RECEIVED_MESSAGES
	 * 
	 */
	@Override
	public void run() {
		try {
			initPlayer();
			while (numberOfSentMessages < 10 || numberOfReceivedMessages < 10) {
				play();
			}
			finalizePlayer();
		} catch (IOException e) {
			log.error("[{}] stoped due to IO Exception.", Thread.currentThread().getName(), e);
		}

	}

	
	/**
	 * method used to send and receive messages 
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	private void play() throws IOException {

		if (bufferedReader.ready()) {
			StringBuilder stringBuilder = new StringBuilder();
			if (numberOfReceivedMessages < 10) {
				String receivedMessage = bufferedReader.readLine();
				log.info("[{}] receive message {}.", Thread.currentThread().getName(), receivedMessage);
				stringBuilder.append(receivedMessage).append(" ").append(numberOfSentMessages);
				numberOfReceivedMessages++;
			}

			if (numberOfSentMessages < 10) {
				log.info("[{}] sent message {}.", Thread.currentThread().getName(), stringBuilder);
				printWriter.println(stringBuilder.toString());
				printWriter.flush();
				numberOfSentMessages++;
			}

		}
	}

	/**
	 * method is used to finalize player and close open sockets and streams
	 * 
	 * @throws IOException If an I/O error occurs
	 */
	protected void finalizePlayer() throws IOException {

		bufferedReader.close();
		printWriter.close();
		outputStream.close();
		inputStream.close();
		socket.close();
		serverSocket.close();
		log.info("[{}] ended.", Thread.currentThread().getName());
	}

}
