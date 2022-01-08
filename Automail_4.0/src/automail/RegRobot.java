package automail;

import simulation.IMailDelivery;

public class RegRobot extends Robot {
	private static final int SPEED = 1; 
	private static final int TUBE = 1; 
	private static final int HAND = 1;

	public RegRobot(IMailDelivery delivery, MailPool mailPool, int number) {
		super(delivery, mailPool, number, SPEED, TUBE, HAND, new RegRobotStorage()); 
	}
}
