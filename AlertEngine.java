import java.util.HashMap;
import java.util.Map;

class AlertEngine{
	
    public Day[] generatedDays;
	public int threshold = 0;
	public Map<Integer, Double> dailyCounter = new HashMap<Integer, Double>();
	public Map<String, Double> averages = new HashMap<String, Double>();
	public Map<String, Double> stdDevs = new HashMap<String, Double>();
	
	public AlertEngine(Event[] baseEvents, Day[] generatedDays, Map<String, Double> averages, Map<String, Double> stdDevs) {
		this.generatedDays = generatedDays;
		this.averages = averages;
		this.stdDevs = stdDevs;
        
        // Calculate threshold
		for (int i = 0; i < baseEvents.length;i++) {
			threshold += baseEvents[i].info.weight * 2;
		}
	}
	
	public void genDailyCount() {
		
		for (int i  = 0; i < generatedDays.length; i++) {
			Double sum = 0.0;
			for (int j = 0; j < generatedDays[i].events.length; j++) {
                Event event = generatedDays[i].events[j];
				Double eventAvg = averages.get(event.name);
				Double eventStdDev = stdDevs.get(event.name);
				if (event.info.eventType == EventType.Discrete) {
					sum += Math.abs((((double)(event.intValue) - eventAvg) / eventStdDev)) * event.info.weight;
				} else {
					sum += Math.abs(((event.doubleValue - eventAvg) / eventStdDev)) * event.info.weight;
				}
			}
            dailyCounter.put(i, sum);
		}
	}
	
	public void detectAnomaly() {
		for (int i = 0; i < dailyCounter.size(); i++) {
			Double counter = dailyCounter.get(i);
			System.out.print("Day " + Integer.toString(i + 1) + "\t: " + counter + "\t");
			if (counter < threshold) {
				System.out.println("No Anomalies");
			}
			else{
				System.out.println("!!! Anomaly Detected");
			}
			System.out.println();
		}
	}
	
}
