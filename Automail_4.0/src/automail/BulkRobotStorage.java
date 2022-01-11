package automail;


import exceptions.ItemTooHeavyException;

public class BulkRobotStorage implements RobotStorage {
	private static final int INDIVIDUAL_MAX_WEIGHT = 2000;  
	private static final int SIZE_TUBE = 5;
	private static final String TYPE = "B";
    private MailItem[] tube_stack = new MailItem[SIZE_TUBE]; 
    private MailItem deliveryItem = null; 
    
    /**
     * check if the robot is carrying anything
     */
    @Override
    public int isEmpty() {
    	for (int i = 0; i < SIZE_TUBE; i++) {
    		if (tube_stack[i] != null) {
    			return 0; 
    		}
    	}
    	if (deliveryItem == null) {
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
    	for (int i = 0; i < SIZE_TUBE; i++) {
    		if (tube_stack[i] == null) {
    			return 0; 
    		} else {
    			continue; 
    		}
    	}
    	return 1;
    }
     
    /**
     * take the next item in the tube 
     */
    @Override
    public MailItem getTube() {
    	// go through all 5 spots in the tube to spot the item on top
    	for (int i = SIZE_TUBE - 1; i >= 0; i--) {
    		if (tube_stack[i] == null) {
    			continue; 
    		} else {
    			return tube_stack[i]; 
    		}
    	}
    	return null; // there are no items found in the tube
    }
    
    /**
     * change the item in the tube...do nothing here
     */
    @Override
    public void setTube(MailItem item) {
    }
    
    /**
     * change the item in the tube
     */
    @Override 
    public void removeTubeItem() {
    	// go through all 5 spots in the tube to spot the item on top
    	for (int i = SIZE_TUBE - 1; i >= 0; i--) {
    		if (tube_stack[i] == null) {
    			continue; 
    		} else {
    			tube_stack[i] = null; 
    			break; 
    		}
    	}
    }
    
    /**
     * add item to the hand...well, there is none here 
     */
    @Override 
    public void addToHand(MailItem mailItem) throws ItemTooHeavyException {
	}

    /**
     * add item to the tube
     */
    @Override 
	public void addToTube(MailItem mailItem) throws ItemTooHeavyException {
		assert(isEmpty() == 1);
		
    	for (int i = 0; i < SIZE_TUBE; i++) {
    		if (tube_stack[i] == null) {
    			tube_stack[i] = mailItem; 
    			deliveryItem = mailItem; 
    			break; 
    		} else {
    			continue; 
    		}
    	}
    	
		if (mailItem.weight > INDIVIDUAL_MAX_WEIGHT) throw new ItemTooHeavyException();
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
    	removeTubeItem(); 
    }
    
    /**
     * retrieve this robot's type status 
     */
    @Override 
    public String getType() {
    	return TYPE; 
    }
    
}
