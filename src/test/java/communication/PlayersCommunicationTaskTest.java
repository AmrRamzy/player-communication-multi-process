package communication;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import players.Initiator;
import players.Player;

class PlayersCommunicationTaskTest {

	@Test()
	@DisplayName("handling missing integer port (first arg)")
	void test_main_no_port() {
		try (MockedStatic<PlayersCommunicationTask> playersCommunicationTaskMock = Mockito
				.mockStatic(PlayersCommunicationTask.class)) {
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.startPlayer(any(Player.class), any()))
					.thenThrow(NullPointerException.class);
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.main(any())).thenCallRealMethod();
			String args[] = new String[0];
			assertAll(() -> PlayersCommunicationTask.main(args));
		}
	}

	@Test()
	@DisplayName("handling none integer port (first arg)")
	void test_main_port_invalid() {
		try (MockedStatic<PlayersCommunicationTask> playersCommunicationTaskMock = Mockito
				.mockStatic(PlayersCommunicationTask.class)) {
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.startPlayer(any(Player.class), any()))
					.thenThrow(NullPointerException.class);
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.main(any())).thenCallRealMethod();
			String args[] = { "99abc" };
			assertAll(() -> PlayersCommunicationTask.main(args));
		}
	}

	@Test()
	@DisplayName("handling integer port (first arg) outside range (0 and 65535)")
	void test_main_port_out_of_range() {
		try (MockedStatic<PlayersCommunicationTask> playersCommunicationTaskMock = Mockito
				.mockStatic(PlayersCommunicationTask.class)) {
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.startPlayer(any(Player.class), any()))
					.thenThrow(NullPointerException.class);
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.main(any())).thenCallRealMethod();
			String args[] = { "999999" };
			assertAll(() -> PlayersCommunicationTask.main(args));
		}
	}
	
	@Test()
	@DisplayName("if second arg is not sent player should be started with default name")
	void test_main_player_default() {
		try (MockedStatic<PlayersCommunicationTask> playersCommunicationTaskMock = Mockito
				.mockStatic(PlayersCommunicationTask.class)) {
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.startPlayer(any(Player.class), any()))
					.thenAnswer(Answers.RETURNS_DEFAULTS);
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.main(any())).thenCallRealMethod();
			String args[] = { "9999" };

			assertAll(() -> PlayersCommunicationTask.main(args));
			playersCommunicationTaskMock
					.verify(() -> PlayersCommunicationTask.startPlayer(any(Player.class), eq("player-2")));
		}
	}

	@Test()
	@DisplayName("if second arg is not (initiator) player should be started with second param as name")
	void test_main_player_name() {
		try (MockedStatic<PlayersCommunicationTask> playersCommunicationTaskMock = Mockito
				.mockStatic(PlayersCommunicationTask.class)) {
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.startPlayer(any(Player.class), any()))
					.thenAnswer(Answers.RETURNS_DEFAULTS);
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.main(any())).thenCallRealMethod();
			String args[] = { "9999", "newPlayer" };

			assertAll(() -> PlayersCommunicationTask.main(args));
			playersCommunicationTaskMock
					.verify(() -> PlayersCommunicationTask.startPlayer(any(Player.class), eq("newPlayer")));
		}
	}

	@Test()
	@DisplayName("if second arg is (initiator) initiator player should be started")
	void test_main_initiator() {
		try (MockedStatic<PlayersCommunicationTask> playersCommunicationTaskMock = Mockito
				.mockStatic(PlayersCommunicationTask.class)) {
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.startPlayer(any(Player.class), any()))
					.thenAnswer(Answers.RETURNS_DEFAULTS);
			playersCommunicationTaskMock.when(() -> PlayersCommunicationTask.main(any())).thenCallRealMethod();
			String args[] = { "9999", "initiator" };

			assertAll(() -> PlayersCommunicationTask.main(args));
			playersCommunicationTaskMock
					.verify(() -> PlayersCommunicationTask.startPlayer(any(Initiator.class), eq("initiator")));
		}
	}

}
