package automail;

public class ServiceFeeFactory {
	private static ServiceFeeFactory instance; 
	private ServiceFeeAdapter serviceFeeAdapter;  
	
	private ServiceFeeFactory() {
	}
	
	public static ServiceFeeFactory getInstance() {
		if (instance == null) {
			instance = new ServiceFeeFactory(); 
			return instance; 
		} else {
			return instance;
		}
	}
	
	/**
	 * get the adapter type 
	 */
	public ServiceFeeAdapter getServiceFeeAdapter() {
		serviceFeeAdapter = new BMSFeeAdapter(Building.getInstance());
		return serviceFeeAdapter; 
	}

}
