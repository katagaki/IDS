import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

// The main class for the program

class IDS {
    
    public static void main(String[] args) {
        Boolean isProgramGoingToQuit = false;
        String eventsFileName = args[0];
        String statsFileName = args[1];
        String numberOfDays = args[2];
        int numberOfEventsInEventsFile = -1;
        int numberOfEventsInStatsFile = -1;
        
        System.out.println("Loading initial event data...");
        try {
            String[] lines = new FileArrayProvider().readLines(Paths.get(eventsFileName).normalize().toString());
            for (String line : lines) {
                if (numberOfEventsInEventsFile == -1) {
                    numberOfEventsInEventsFile = Integer.parseInt(line);
                } else {
                    // Parse line split by colons
                    String[] lineSplitByColon = line.split(":");
                    String eventName = lineSplitByColon[0];
                    String eventType = lineSplitByColon[1];
                    String eventMin = lineSplitByColon[2];
                    String eventMax = lineSplitByColon[3];
                    String eventWeight = lineSplitByColon[4];
                    EventInfo newEventInfo = new EventInfo((eventType == "C" ? EventType.Continuous : EventType.Discrete),
                                                           (eventMin == "" ? 0.0 : Double.parseDouble(eventMin)),
                                                           (eventMax == "" ? 0.0 : Double.parseDouble(eventMax)),
                                                           (eventMin == "" ? true : false),
                                                           (eventMax == "" ? true : false),
                                                           Integer.parseInt(eventWeight));
                    Event newEvent = new Event(eventName, newEventInfo, new Stats());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Loading initial event stats data...");
        // TODO: Check initial input for consistencies
        //       - Ensure that number of events match the number specified in the file
        //       - Ensure that number of events match between 2 files
        //       - Minimum must be smaller than maximum
        //       - Mean must be between minimum and maximum
        //       - Continuous minimum and maximum must be provided *
        //       - Discrete must be integer
        //       - Continous events must be 2 decimal places
        //       - All values must be positive
        // TODO: Generate 'baseline' data: the initial set of per-day event statistics that are considered acceptable
        
        while (!isProgramGoingToQuit) {
            isProgramGoingToQuit = true;
        }
        
    }
    
}
