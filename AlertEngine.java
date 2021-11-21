
import java.util.HashMap;
import java.util.Map;

class AlertEngine{
	
	public Stats baseStats;
	public Stats newStats;
	public Event[] baseEvents[];
    public Day[] generatedDays;
	public int threshold;
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
	}
	
	public void genDailyCount(){
		
		for (int i  = 0; i < generatedDays.length;i++){
			Double sum = 0.0;
			for (int j = 0; j < generatedDays.Event.length;j++){
				Double eventAvg = 0.0;
				Double eventStdDev = 0.0;
				for (Map<String, Double> key : averages.entrySet()){
					if (key.containsKey(generatedDays.Event.Name)){
						eventAvg = key.getValue;
					}
				}
				for (Map<String, Double> key : stdDevs.entrySet()){
					if (key.containsKey(generatedDays.Event.Name)){
						eventStdDev = key.getValue;
					}
				}
				if (event.info.eventType == EventType.Discrete) {
					sum += (((double)event.intValue - eventAvg)/ eventStdDev) * generatedDays.events.info.weight;
				} 
				else {
					sum += sum += ((event.DoubleValue - eventAvg)/ eventStdDev) * generatedDays.events.info.weight;
				}
				String temp = "Days " + (i+1);
				dailyCounter.put(temp,sum);
			}
		}
	}
	
}
