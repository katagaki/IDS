import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;


public class AnalysisEngine {
	
	public Double[] dailyTotals;
	public Day[] days;
	public Map<String, ArrayList<Event>> events = new HashMap<String, ArrayList<Event>>();
	public Map<String, Double> averages = new HashMap<String, Double>();
	public Map<String, Double> stdDevs = new HashMap<String, Double>();
	
	// averages.put("EventName", average);
	// averages.get("EventName"); --> return Double
	
	public AnalysisEngine (Day[] days) {
		
		this.days = days;
		dailyTotals = new Double[days.length];
		analyzedValues = new Double[days.length];
	}
	
	public void analyze() {
		
		// Calculate daily totals per day
		for (int i = 0; i < days.length; i++) {
			
			Double sum = 0;
			for (Event event : days[i].events) {
				if (event.info.eventType == EventType.Discrete) {
					sum += (double)event.intValue;
				} else {
					sum += event.doubleValue;
				}
			}
			dailyTotals[i] = sum;
			
			// Convert events per day to days per event
			for (Event event : days[i].events) {
				if (!events.containsKey(event.name)) {
					events.put(event.name, new Event[] {event});
				} else {
					events.get(event.name).add(event);
				}
			}
			
		}
		
		for (String key : events.keySet()) {
			ArrayList<Event> eventArray = events.get(key);
			ArrayList<Double> eventValues = new ArrayList<Double>();
			for (Event event : eventArray) {
				if (event.info.eventType == EventType.Discrete) {
					eventValues.add((Double)event.intValue);
				} else {
					eventValues.add(event.doubleValue);
				}
			}
			// Calculate standard deviation for each event
			Double average = avg(eventValues.toArray());
			Double stdDev = stdDev(eventValues.toArray());
			
			averages.put(event.name, average);
			stdDevs.put(event.name, stdDev);
			
		}
		
		for (int i = 0; i < days.length; i++) {
			
			for (int j = 0; j < days[i].events.length; j++) {
				Event event = days[i].events[j];
				Double eventValue = 0.0;
				if (event.info.eventType == EventType.Discrete) {
					eventValue = (Double)event.intValue;
				} else {
					eventValue = event.doubleValue;
				}
				event.analyzedValue = Math.abs(averages.get(event.name) - eventValue) / stdDevs.get(event.name) * event.info.weight;
			}
		}
		
	}
	
	public void saveStatsFile() {
		
	     String output = "";
	     
	     // Generate and append event info to output
	     for (String key : events.keySet()) {
	    	 DecimalFormat df = new DecimalFormat("#.00");
	    	 Double average = averages.get(key);
	    	 Double stdDev = stdDevs.get(key);
	    	 String eventLine = key + ":" + df.format(average) + ":" + df.format(stdDev) + ":";
	    	 output = output + eventLine + "\n";
	     }
	    
	    // Save stats output to file
		try {       
	        FileWriter writer = new FileWriter("NewStats.txt");
	        writer.append(output);
	        writer.flush();	
	        writer.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}

	
	// From https://www.programiz.com/java-programming/examples/standard-deviation
	public Double stdDev(Double numArray[])
    {
		Double stdDev = 0.0;
		Double mean = avg(numArray);

        for(Double num: numArray) {
        	stdDev += Math.pow(num - mean, 2);
        }

        return Math.sqrt(stdDev/length);
    }
	
	public Double avg(Double numArray[]) {
		
		Double sum = 0.0; 
        int length = numArray.length;

        for(Double num : numArray) {
            sum += num;
        }

        return sum/length;

	}

	
}