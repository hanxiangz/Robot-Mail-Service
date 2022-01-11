package automail;

import simulation.IMailDelivery;

public class BulkRobot extends Robot {
	private static final int SPEED = 1; 
	private static final int TUBE = 1; 
	private static final int HAND = 0;
	private static final double TYPE_RATE = 0.01;
	private static int numInstances = 0; 
	private static int totalOp = 0; 
	
	public BulkRobot(IMailDelivery delivery, MailPool mailPool, int number) {
		super(delivery, mailPool, number, SPEED, TUBE, HAND, new BulkRobotStorage()); 
		numInstances++; 
	}
	
	@Override 
	public double getTypeRate() {
		return TYPE_RATE; 
	}
	
	@Override
	public double average_num_steps() {
		return totalOp / numInstances; 
	}
	
	@Override 
	public void increment_totalOp() {
		totalOp++; 
	}

}
