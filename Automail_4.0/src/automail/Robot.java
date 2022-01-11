package automail;

import automail.Robot.RobotState;
import exceptions.ExcessiveDeliveryException;
import simulation.Clock;
import simulation.IMailDelivery;

// need to implement tube is full method to make bulk robot work
public abstract class Robot {
	
    private final String id;
    private IMailDelivery delivery;
    /** Possible states the robot can be in */
    public enum RobotState { DELIVERING, WAITING, RETURNING }
    private RobotState current_state;
    private int current_floor;
    private int destination_floor;
    private MailPool mailPool;
    private boolean receivedDispatch;
    private int deliveryCounter; 
    private RobotStorage storage; 
    private final int speed;  
    private final int containsTube; 
    private final int containsHand; 
    private ChargeHandler charge_handler = new ChargeHandler(); 
    
	
	/**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     */
    public Robot(IMailDelivery delivery, MailPool mailPool, int number, int speed, int containsTube, 
    		int containsHand, RobotStorage storage) {
    	this.storage = storage; 
    	this.id = storage.getType() + number; 
        // current_state = RobotState.WAITING;
    	current_state = RobotState.RETURNING;
        current_floor = Building.getInstance().getMailroomLocationFloor();
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.receivedDispatch = false;
        this.deliveryCounter = 0;
        this.speed = speed; 
        this.containsTube = containsTube; 
        this.containsHand = containsHand; 
    }
    
    /** 
     * returns 1 if this robot has a tube 
     */
    public int hasTube() {
    	return containsTube; 
    }
    
    /** 
     * returns 1 if this robot has a hand 
     */
    public int hasHand() {
    	return containsHand; 
    }
    
    /**
     * This is called when a robot is assigned the mail items and ready to dispatch for the delivery 
     */
    public void dispatch() {
    	receivedDispatch = true;
    }
    
    /**
     * get the current floor of this robot
     */
    public int get_current_floor() {
    	return current_floor; 
    }
    
    /** 
     * This is called if another class wants to access robot storage
     */
    public RobotStorage getStorage() {
    	return storage; 
    }
    
    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public void operate() throws ExcessiveDeliveryException {   
    	switch(current_state) {
    		/** This state is triggered when the robot is returning to the mailroom after a delivery */
    		case RETURNING:
    			increment_totalOp(); 
    			/** If its current position is at the mailroom, then the robot should change state */
                if(current_floor == Building.getInstance().getMailroomLocationFloor()) {
        			/** Tell the sorter the robot is ready */
        			mailPool.registerWaiting(this);
                	changeState(RobotState.WAITING);
                } else {
                	/** If the robot is not at the mailroom floor yet, then move towards it! */
                    moveTowards(Building.getInstance().getMailroomLocationFloor());
                	break;
                }
    		case WAITING:
                /** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
                if(storage.isEmpty() == 0 && receivedDispatch) {
                	receivedDispatch = false;
                	deliveryCounter = 0; // reset delivery counter
                	setDestination();
                	changeState(RobotState.DELIVERING);
                }
                break;
    		case DELIVERING:
    			increment_totalOp(); 
    			if(current_floor == destination_floor) { // If already here drop off either way
    				/** add charge, fee, maintenance */ 
    				String additional_log = charge_handler.charge(this); 
                    /** Delivery complete, report this to the simulator! */
    				delivery.deliver(this, storage.getDeliveryItem(), additional_log);
                    storage.removeDeliveryItem(); 
                    deliveryCounter++; 
                    if(deliveryCounter > 2) {  // Implies a simulation bug
                    	throw new ExcessiveDeliveryException();
                    }
                    /** Check if want to return, i.e. if there is no item in the tube*/
                    if(storage.getTube() == null) {
                    	changeState(RobotState.RETURNING);
                    }
                    else {
                        /** If there is another item, set the robot's route to the location to deliver the item */
                        storage.setDeliveryItem(storage.getTube());
                        storage.setTube(null); // this might be causing issue, logic error 
                        setDestination();
                        changeState(RobotState.DELIVERING);
                    }
                    
    			} else {
	        		/** The robot is not at the destination yet, move towards it! */
	                moveTowards(destination_floor);
    			}
                break;
    	}
    }
    
    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
    private void moveTowards(int destination) {
    	if(current_floor < destination){
            current_floor += speed;
        } else {
            current_floor--;
        }
    }
    
    /**
     * Sets the route for the robot
     */
    private void setDestination() {
        /** Set the destination floor */
        destination_floor = storage.getDeliveryItem().getDestFloor();
    }
    
    /**
     * Prints out the change in state
     * @param nextState the state to which the robot is transitioning
     */
    private void changeState(RobotState nextState) {
    	assert(!(storage.getDeliveryItem() == null && storage.getTube() != null));
    	if (current_state != nextState) {
            System.out.printf("T: %3d > %7s changed from %s to %s%n", Clock.Time(), getIdTube(), current_state, nextState);
    	}
    	current_state = nextState;
    	if(nextState == RobotState.DELIVERING) {
            System.out.printf("T: %3d > %7s-> [%s]%n", Clock.Time(), getIdTube(), storage.getDeliveryItem().toString());
    	}
    }
    
    /**
     * get the tube's id
     */
    public String getIdTube() {
    	return String.format("%s(%1d)", this.id, (storage.getTube() == null ? 0 : 1)); 
    }
    
    /** 
     * get the type based rate for this robot
     */
    protected abstract double getTypeRate(); 
    
    /**
     * get the average operating time ie.average number of steps that all the robots with the same type as the current 
`    * robot have operated in the past 
     */
    protected abstract double average_num_steps(); 
    
    /**
     * increment the total operating time for a robot type 
     */
    protected abstract void increment_totalOp(); 

}
