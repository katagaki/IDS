import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityEngine {
    
    public int countOfDays;
    public Event[] inputEvents;
    public Day[] generatedDays;
    
    public ActivityEngine(Event[] inputEvents, int countOfDays) {
        this.inputEvents = inputEvents;
        this.countOfDays = countOfDays;
        generatedDays = new Day[countOfDays];
    }
    
    public void generateEvents() {
        // Process each day
        for (int i = 0; i < countOfDays; i++) {
            Day newDay = new Day();
            newDay.events = new Event[inputEvents.length];
            
            // Process each event for each day
            for (int j = 0; j < inputEvents.length; j++) {
                // Create event modelled after existing event
                Event event = inputEvents[j];
                Event newEvent = new Event(event.name, event.info, event.stats);
                newEvent.generate();
                newDay.events[j] = newEvent;
            }
            
            generatedDays[i] = newDay;
        }
    }
    
    public void saveDayFiles() {
        
        // Create folder to store day data
        String folderName = "Days-" + getEpochTimeString();
        File directory = new File(folderName);
        
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Save stats output to file
        try {
            for (int i = 0; i < generatedDays.length; i++) {
                String filename = folderName + "/" + "Day-" + Integer.toString(i + 1) + ".txt";
                FileWriter writer = new FileWriter(filename);
                writer.append(generatedDays[i].toString());
                writer.flush();
                writer.close();
            }
            System.out.println("New activity data saved to the folder '" + folderName + "'.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private String getEpochTimeString() {
        return new SimpleDateFormat("yyyymmddHHmmss").format(Calendar.getInstance().getTime());
    }
    
}
