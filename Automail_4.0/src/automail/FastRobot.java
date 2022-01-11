package automail;

import simulation.IMailDelivery;

public class FastRobot extends Robot {
	private static final int SPEED = 3; 
	private static final int TUBE = 0; 
	private static final int HAND = 1;
	private static final double TYPE_RATE = 0.05;
	private static int numInstances = 0; 
	private static int totalOp = 0; 
	
	public FastRobot(IMailDelivery delivery, MailPool mailPool, int number) {
		super(delivery, mailPool, number, SPEED, TUBE, HAND, new FastRobotStorage()); 
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
