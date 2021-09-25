package communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import players.Initiator;
import players.Player;

/**
 * application entry point
 * 
 * @author Amr Ramzy
 *
 */
public class PlayersCommunicationTask {
	
	public static final Logger log = LoggerFactory.getLogger(PlayersCommunicationTask.class);

	/**
	 * application main method,
	 * creates and start the players threads 
	 * 
	 * @param args arguments for application execution
	 * first argument socket port (mandatory)
	 * second argument initiator to create an initiator player and not required for normal player
	 */
	public static void main(String[] args) {
		
		if(args.length>0) {
			String portValue = args[0];
			
			try {
				int port = Integer.parseInt(portValue);
				if(port>0 && port <65535) {
					Player player = new Player(port);
					String name = "player-2";
					if (args.length > 1 ) {
						name = args[1];
						if("initiator".equalsIgnoreCase(name)) {
							player = new Initiator(port);
						}
					}
					
					startPlayer(player, name);
				}else {
					log.error("invalid port number : invalid port value ({}) , please select a free port between 0 and 65535", portValue);
				}
				
			} catch (NumberFormatException e) {
				log.error("invalid port number : port value entered ({}) is not a valid integer", portValue, e);
			}
			
		}else {
			log.error("invalid port number : please pass port value as first argument");
		}
		

	}

	static void startPlayer(Player player, String name) {
		new Thread(player,name).start();
	}

}
