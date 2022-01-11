package automail;

import exceptions.ItemTooHeavyException;

public class FastRobotStorage implements RobotStorage {
	private static final int INDIVIDUAL_MAX_WEIGHT = 2000;  
    private MailItem deliveryItem = null; 
    private static final String TYPE = "F";
    
    /**
     * check if the robot is carrying anything
     */
    @Override
    public int isEmpty() {
    	if (deliveryItem == null) {
    		return 1; 
    	} else {
    		return 0; 
    	} 
    }
    
    /**
     * check to see if the tube is full...there is no tube, so return 0
     */
    @Override
    public int tubeIsFull() {
    	return 0; 
    }
     
    /**
     * take the next item on the tube 
     */
    @Override
    public MailItem getTube() {
    	return null; 
    }
    
    /**
     * change the item in the tube...well, there is no tube here
     */
    @Override
    public void setTube(MailItem item) {
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
     * add item to the tube...wait, there is no tube here
     */
    @Override 
	public void addToTube(MailItem mailItem) throws ItemTooHeavyException {
	}
    
    /**
     * change the item in the tube...well, there's no tube in this robot
     */
    @Override 
    public void removeTubeItem() {
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
