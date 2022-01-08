package automail;

import simulation.IMailDelivery;

public class BulkRobot extends Robot {
	private static final int SPEED = 1; 
	private static final int TUBE = 1; 
	private static final int HAND = 0;
	
	public BulkRobot(IMailDelivery delivery, MailPool mailPool, int number) {
		super(delivery, mailPool, number, SPEED, TUBE, HAND, new BulkRobotStorage()); 
	}

}
