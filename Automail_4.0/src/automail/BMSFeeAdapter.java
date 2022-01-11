package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

public class BMSFeeAdapter implements ServiceFeeAdapter {
	
	private Building building; 
	
	public BMSFeeAdapter(Building building) {
		this.building = building; 
	}
	
	@Override 
	public double getServiceFee(int floor) {
		try {
            WifiModem wifi = WifiModem.getInstance(building.getMailroomLocationFloor());
            return wifi.forwardCallToAPI_LookupPrice(floor);
        } catch (Exception e) {
            return -1.0;
        }

	}
}
