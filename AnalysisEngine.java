import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;


public class AnalysisEngine {
	
	public Double[] dailyTotals;
	public Day[] days;
	public Map<String, ArrayList<Event>> events = new HashMap<String, ArrayList<Event>>();
	public Map<String, Double> averages = new HashMap<String, Double>();
	public Map<String, Double> stdDevs = new HashMap<String, Double>();
	
	public AnalysisEngine (Day[] days) {
		this.days = days;
		dailyTotals = new Double[days.length];
	}
	
	public void analyze() {
		
		// Calculate daily totals per day
		for (int i = 0; i < days.length; i++) {
			
			Double sum = 0.0;
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
                    ArrayList<Event> newArrayList = new ArrayList<Event>();
                    newArrayList.add(event);
					events.put(event.name, newArrayList);
				} else {
					events.get(event.name).add(event);
				}
			}
			
		}
		
        // Calculate average and standard deviation per event
		for (String key : events.keySet()) {
			ArrayList<Event> eventArray = events.get(key);
			ArrayList<Double> eventValues = new ArrayList<Double>();
			for (Event event : eventArray) {
				if (event.info.eventType == EventType.Discrete) {
					eventValues.add((double)event.intValue);
				} else {
					eventValues.add(event.doubleValue);
				}
			}
			// Calculate standard deviation for each event
			Double average = avg(eventValues.toArray(Double[]::new));
			Double stdDev = stdDev(eventValues.toArray(Double[]::new));
			
			averages.put(key, average);
			stdDevs.put(key, stdDev);
			
		}
		
        // Analyze and set the analyzed value for each event datapoint
		for (int i = 0; i < days.length; i++) {
			
			for (int j = 0; j < days[i].events.length; j++) {
				Event event = days[i].events[j];
				Double eventValue = 0.0;
				if (event.info.eventType == EventType.Discrete) {
					eventValue = (double)event.intValue;
				} else {
					eventValue = event.doubleValue;
				}
				event.analyzedValue = Math.abs(averages.get(event.name) - eventValue) / stdDevs.get(event.name) * event.info.weight;
			}
		}
		
	}
	
	public void saveStatsFile() {
		
	     String output = Integer.toString(events.size()) + "\n";
	     
	     // Generate and append event info to output
	     for (String key : events.keySet()) {
	    	 DecimalFormat df = new DecimalFormat("0.00");
	    	 Double average = averages.get(key);
	    	 Double stdDev = stdDevs.get(key);
	    	 String eventLine = key + ":" + df.format(average) + ":" + df.format(stdDev) + ":";
	    	 output = output + eventLine + "\n";
	     }
	    
	    // Save stats output to file
		try {
            String filename = "Stats-" + getEpochTimeString() + ".txt";
	        FileWriter writer = new FileWriter(filename);
	        writer.append(output);
	        writer.flush();	
	        writer.close();
            System.out.println("New stats data saved to '" + filename + "'.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
    
    public void saveDailyTotalsFile() {
        
         String output = "";
         
         // Generate and append event info to output
         for (int i = 0; i < dailyTotals.length; i++) {
             
             String dailyTotalLine = Integer.toString(i + 1) + ":" + Double.toString(dailyTotals[i]);
             output = output + dailyTotalLine + "\n";
         }
        
        // Save daily totals output to file
        try {
            String filename = "DailyTotals-" + getEpochTimeString() + ".txt";
            FileWriter writer = new FileWriter(filename);
            writer.append(output);
            writer.flush();
            writer.close();
            System.out.println("Daily totals data saved to '" + filename + "'.");
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

        return Math.sqrt(stdDev / numArray.length);
    }
	
	public Double avg(Double numArray[]) {
		
		Double sum = 0.0; 
        int length = numArray.length;

        for(Double num : numArray) {
            sum += num;
        }

        return sum/length;

	}
    
    private String getEpochTimeString() {
        return new SimpleDateFormat("yyyymmddHHmmss").format(Calendar.getInstance().getTime());
    }
	
}
