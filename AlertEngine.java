
import java.util.HashMap;
import java.util.Map;

class AlertEngine{
	
	public Stats baseStats;
	public Stats newStats;
	public Event[] baseEvents[];
    public Day[] generatedDays;
	public int threshold = 0;
	public Map<String, Double> dailyCounter = new HashMap<String, Double>();
	public Map<String, Double> averages = new HashMap<String, Double>();
	public Map<String, Double> stdDevs = new HashMap<String, Double>();
	
	
	public AlertEngine(Stats newStats, Stats baseStats, Event[] baseEvents,Day[] generatedDays, Map<String, Double> averages, Map<String, Double> stdDevs){
		this.newStats = newStats;
		this.baseStats = baseStats;
		this.baseEvents = baseEvents;
		this.generatedDays = generatedDays;
		this.averages = averages;
		this.stdDevs = stdDevs;
		for (int i = 0; i < baseEvents.length;i++){
			threshold += baseEvents.events.info.weight * 2;
		}
	}
	
	public void genDailyCount(){
		
		for (int i  = 0; i < generatedDays.length;i++){
			Double sum = 0.0;
			for (int j = 0; j < generatedDays.Event.length;j++){
				Double eventAvg = averages.get(generatedDays.events.name);
				Double eventStdDev = stdDevs.get(generatedDays.events.name);
				if (event.info.eventType == EventType.Discrete) {
					sum += (((double)event.intValue - eventAvg)/ eventStdDev) * generatedDays.events.info.weight;
				} 
				else {
					sum += ((event.DoubleValue - eventAvg)/ eventStdDev) * generatedDays.events.info.weight;
				}
				String temp = "Days " + (i+1);
				dailyCounter.put(temp,sum);
			}
		}
	}
	
	public void detectAnomaly(){
		String temp = "";
		for (int i = 0; i < dailyCounter.length;i++){
			temp = "Days " + i;
			Double counter = dailyCounter.get("temp";)
			System.out.println(temp + " : " + counter);
			if (counter < threshold){
				System.out.println("Ok");
			}
			else{
				System.out.println("Alert");
			}
			System.out.println("\n");
		}
	}
	}
	
}
