import java.util.HashMap;
import java.util.Map;

class AlertEngine{
    
    public Day[] generatedDays;
    public int threshold = 0;
    public Map<Integer, Double> dailyCounter = new HashMap<Integer, Double>();
    
    public AlertEngine(Event[] baseEvents, Day[] generatedDays) {
        this.generatedDays = generatedDays;
        
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
                if (event.info.eventType == EventType.Discrete) {
                    sum += event.analyzedValue;
                } else {
                    sum += event.analyzedValue;
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
        }
        System.out.println();
    }
    
}
