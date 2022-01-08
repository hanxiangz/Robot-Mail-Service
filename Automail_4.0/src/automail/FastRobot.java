package automail;

import simulation.IMailDelivery;

public class FastRobot extends Robot {
	private static final int SPEED = 3; 
	private static final int TUBE = 0; 
	private static final int HAND = 1;
	
	public FastRobot(IMailDelivery delivery, MailPool mailPool, int number) {
		super(delivery, mailPool, number, SPEED, TUBE, HAND, new FastRobotStorage()); 
	}

}
