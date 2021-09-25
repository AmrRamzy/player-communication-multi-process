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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
class InitiatorTest {

	private static final int PORT = 9897;
	protected Initiator player;
	protected Initiator playerMock;
	protected Socket socketMock = Mockito.mock(Socket.class);
	protected ServerSocket serverSocketMock = Mockito.mock(ServerSocket.class);
	protected BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
	protected PrintWriter printWriterMock = Mockito.mock(PrintWriter.class);
	protected OutputStream outputStreamMock = Mockito.mock(OutputStream.class);
	protected InputStream inputStreamMock = Mockito.mock(InputStream.class);

	@BeforeEach
	void initTest() throws IOException {
		player = new Initiator(PORT);
		player.serverSocket = serverSocketMock;
		player.socket = socketMock;
		player.printWriter = printWriterMock;
		player.bufferedReader = bufferedReaderMock;
		player.outputStream = outputStreamMock;
		player.inputStream = inputStreamMock;
		playerMock = Mockito.spy(player);

	}

	@Test()
	@DisplayName("test Initiator player sending first message")
	void test_initiator_send_initial_message() {
		assertAll(() -> playerMock.sendInitialMessage());
		assertEquals(1, playerMock.numberOfSentMessages,
				"number of sent messages should be 1 due to sending first message");
	}

	@Test()
	@DisplayName("test player finalization")
	void test_player_finalizePlayer() {
		assertAll(() -> playerMock.finalizePlayer());
	}

}
