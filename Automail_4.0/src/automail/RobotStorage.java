package automail;

import exceptions.ItemTooHeavyException;

public interface RobotStorage {
	
	void addToHand(MailItem mailItem) throws ItemTooHeavyException;
	
	void addToTube(MailItem mailItem) throws ItemTooHeavyException; 
	
	int isEmpty(); 
	
	int tubeIsFull(); 
	
	MailItem getTube(); 
	
	void setTube(MailItem item); 
	
	void removeTubeItem(); 
	
	MailItem getDeliveryItem(); 
	
	void setDeliveryItem(MailItem item); 
	
	void removeDeliveryItem();  
    
    String getType(); 

}
