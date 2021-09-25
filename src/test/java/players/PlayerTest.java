package players;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerTest {

	private static final int PORT = 9897;
	protected Player player;
	protected Player playerMock;
	protected Socket socketMock = Mockito.mock(Socket.class);
	protected ServerSocket serverSocketMock = Mockito.mock(ServerSocket.class);
	protected BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
	protected PrintWriter printWriterMock = Mockito.mock(PrintWriter.class);
	protected OutputStream outputStreamMock = Mockito.mock(OutputStream.class);
	protected InputStream inputStreamMock = Mockito.mock(InputStream.class);

	@BeforeEach
	void initTest() throws IOException {
		player = new Player(PORT);
		player.serverSocket = serverSocketMock;
		player.socket = socketMock;
		player.printWriter = printWriterMock;
		player.bufferedReader = bufferedReaderMock;
		player.outputStream = outputStreamMock;
		player.inputStream = inputStreamMock;
		playerMock = Mockito.spy(player);

		Mockito.doNothing().when(playerMock).initPlayer();
		Mockito.doReturn(true).when(bufferedReaderMock).ready();
		Mockito.doReturn("dummy String").when(bufferedReaderMock).readLine();
	}

	@Test()
	@DisplayName("test player should send 10 messages")
	void test_player_run_sent_messages() {
		assertAll(() -> playerMock.run());
		assertEquals(10, playerMock.numberOfSentMessages, "number of sent messages should be 10");
	}

	@Test()
	@DisplayName("test player should receive 10 messages")
	void test_player_run_received_messages() {
		assertAll(() -> playerMock.run());
		assertEquals(10, playerMock.numberOfReceivedMessages, "number of received messages should be 10");
	}

	@Test()
	@DisplayName("test player finalization")
	void test_player_finalizePlayer() {
		assertAll(() -> playerMock.finalizePlayer());
	}

}
