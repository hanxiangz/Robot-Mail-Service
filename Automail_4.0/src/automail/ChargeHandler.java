package automail;
import java.util.HashMap;
import java.util.Map;


public class ChargeHandler {

	private static final double DEFAULT_SERVICE_FEE = 0.0;
	private final Map<Integer, Double> floorServiceFees = new HashMap<>(); 
	
	public ChargeHandler() {
	}
	
	/**
	 * @param takes a robot to determine which floor it is on
	 * @return a string containing charge, a fee, and a maintenance cost
	 */
	public String charge(Robot robot) {
		double serviceFee = calculate_serviceFee(robot.get_current_floor()); 
		double maintenance_cost = robot.getTypeRate() * averageOpTime(robot); 
		double averageOpTime = averageOpTime(robot); 
		double total_charge = serviceFee + maintenance_cost; 
		
		return String.format("Service Fee : %f | Maintenance: %f | Avg. Operating Time: %f | Total Charge: %f", 
				serviceFee, maintenance_cost, averageOpTime, total_charge); 
	}
	
	private double averageOpTime(Robot robot) {
		return robot.average_num_steps(); 
	}
	
	private double calculate_serviceFee(int floor) {
		double fee = ServiceFeeFactory.getInstance().getServiceFeeAdapter().getServiceFee(floor); 
		if (fee > 0) {
			floorServiceFees.put(floor, fee); 
			return fee;
		} else {
			return floorServiceFees.getOrDefault(floor, DEFAULT_SERVICE_FEE);
		}
		
	}
}
