package automail;

import simulation.IMailDelivery;

public class Automail {

    private Robot[] robots;
    private MailPool mailPool;
    
    public Automail(MailPool mailPool, IMailDelivery delivery, int numRegRobots, int numFastRobots, int numBulkRobots) {  	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	
    	/** Initialize robots, currently only regular robots */
    	int total = numRegRobots + numFastRobots + numBulkRobots; 
    	robots = new Robot[total];
    	int count = 0; 
    	
    	for (int i = 0; i < numRegRobots; i++) { 
    		robots[count] = new RegRobot(delivery, mailPool, count);
    		count++; 
    	}
    	
    	for (int j = 0; j < numFastRobots; j++) {
    		robots[count] = new FastRobot(delivery, mailPool, count);
    		count++;
    	}
    	
    	for (int k = 0; k < numBulkRobots; k++) {
    		robots[count] = new BulkRobot(delivery, mailPool, count);
    		count++; 
    	}
    	
    }

    public Robot[] getRobots() {
        return robots;
    }

    public MailPool getMailPool() {
        return mailPool;
    }
}
