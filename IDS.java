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
        Event[] inputEvents = {};
        
        System.out.println("Loading initial event data...");
        try {
            String[] lines = new FileArrayProvider().readLines(Paths.get(eventsFileName).normalize().toString());
            inputEvents = new Event[lines.length - 1];
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
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
                    inputEvents[i - 1] = new Event(eventName, newEventInfo, new Stats());
                }
            }
        } catch (IOException e) {
            System.out.println("!!! Could not parse event data!\n\nStack trace:\n");
            e.printStackTrace();
        }
        
        System.out.println("Loading initial event stats data...");
        try {
            String[] lines = new FileArrayProvider().readLines(Paths.get(statsFileName).normalize().toString());
            for (String line : lines) {
                if (numberOfEventsInStatsFile == -1) {
                    numberOfEventsInStatsFile = Integer.parseInt(line);
                } else {
                    // Parse line split by colons
                    String[] lineSplitByColon = line.split(":");
                    String eventName = lineSplitByColon[0];
                    String eventMean = lineSplitByColon[1];
                    String eventStdDev = lineSplitByColon[2];
                    Stats newStats = new Stats(Double.parseDouble(eventMean),
                                               Double.parseDouble(eventStdDev));
                    for (Event event : inputEvents) {
                        if (event.name == eventName) {
                            event.stats = newStats;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("!!! Could not parse event stats data!\n\nStack trace:\n");
            e.printStackTrace();
        }
        
        System.out.println("Number of events loaded: " + String.valueOf(inputEvents.length));
        
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
