package automail;

import exceptions.ItemTooHeavyException;


public class RegRobotStorage implements RobotStorage {
	
	private static final int INDIVIDUAL_MAX_WEIGHT = 2000;  
    private MailItem tube; 
    private MailItem deliveryItem = null; 
    private static final String TYPE = "R";
    
    /**
     * check if the robot is carrying anything
     */
    @Override
    public int isEmpty() {
    	if (deliveryItem == null && tube == null) {
    		return 1; 
    	} else {
    		return 0; 
    	}
    }
    
    /**
     * check to see if the tube is full
     */
    @Override
    public int tubeIsFull() {
    	if (tube == null) {
    		return 0; 
    	} else {
    		return 1; 
    	}
    }
     
    /**
     * take the next item on the tube 
     */
    @Override
    public MailItem getTube() {
    	return tube; 
    }
    
    /**
     * change the item in the tube
     */
    @Override
    public void setTube(MailItem item) {
    	tube = item; 
    }
    
    /**
     * change the item in the tube
     */
    @Override 
    public void removeTubeItem() {
    	tube = null; 
    }
    
    /**
     * add item to the hand 
     */
    @Override 
    public void addToHand(MailItem mailItem) throws ItemTooHeavyException {
		assert(deliveryItem == null);
		deliveryItem = mailItem;
		if (deliveryItem.weight > INDIVIDUAL_MAX_WEIGHT) throw new ItemTooHeavyException();
	}

    /**
     * add item to the tube
     */
    @Override 
	public void addToTube(MailItem mailItem) throws ItemTooHeavyException {
		assert(tube == null);
		tube = mailItem;
		if (tube.weight > INDIVIDUAL_MAX_WEIGHT) throw new ItemTooHeavyException();
	}

    
    /**
     * take a peek at the item the robot is delivering 
     */
    @Override
    public MailItem getDeliveryItem() {
    	return deliveryItem; 
    }
    
    /**
     * change the robot's delivery item
     */
    @Override
    public void setDeliveryItem(MailItem item) {
    	deliveryItem = item; 
    }
    
    /**
     * use this method when we have delivered the item
     */
    @Override 
    public void removeDeliveryItem() {
    	deliveryItem = null; 
    }
    
    /**
     * retrieve this robot's type status 
     */
    @Override 
    public String getType() {
    	return TYPE; 
    }
    
    
}
