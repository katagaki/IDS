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
        int numberOfDays = Integer.parseInt(args[2]);
        int numberOfEventsDeclaredInEventsFile = -1;
        int numberOfEventsDeclaredInStatsFile = -1;
        int numberOfEventsParsedInEventsFile = 0;
        int numberOfEventsParsedInStatsFile = 0;
        Event[] inputEvents = {};
        
        // TODO: Validate command line arguments
        
        System.out.print("Loading initial event data...");
        try {
            String[] lines = new FileArrayProvider().readLines(Paths.get(eventsFileName).normalize().toString());
            inputEvents = new Event[lines.length - 1];
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (numberOfEventsDeclaredInEventsFile == -1) {
                    numberOfEventsDeclaredInEventsFile = Integer.parseInt(line);
                } else {
                    // Parse line split by colons
                    String[] lineSplitByColon = line.split(":");
                    if (lineSplitByColon.length == 5) {
                        numberOfEventsParsedInEventsFile += 1;
                        String eventName = lineSplitByColon[0];
                        String eventType = lineSplitByColon[1];
                        String eventMin = lineSplitByColon[2];
                        String eventMax = lineSplitByColon[3];
                        String eventWeight = lineSplitByColon[4];
                        EventInfo newEventInfo = new EventInfo((eventType.equals("C") ? EventType.Continuous : EventType.Discrete),
                                                               (eventMin.equals("") ? 0.0 : Double.parseDouble(eventMin)),
                                                               (eventMax.equals("") ? 0.0 : Double.parseDouble(eventMax)),
                                                               (eventMin.equals("") ? false : true),
                                                               (eventMax.equals("") ? false : true),
                                                               Integer.parseInt(eventWeight));
                        inputEvents[i - 1] = new Event(eventName, newEventInfo, new Stats());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\n!!! Could not parse event data!\n\nStack trace:\n");
            e.printStackTrace();
        }
        System.out.println(" done.");
        
        System.out.print("Loading initial event stats data...");
        try {
            String[] lines = new FileArrayProvider().readLines(Paths.get(statsFileName).normalize().toString());
            for (String line : lines) {
                if (numberOfEventsDeclaredInStatsFile == -1) {
                    numberOfEventsDeclaredInStatsFile = Integer.parseInt(line);
                } else {
                    // Parse line split by colons
                    String[] lineSplitByColon = line.split(":");
                    if (lineSplitByColon.length == 3) {
                        numberOfEventsParsedInStatsFile += 1;
                        String eventName = lineSplitByColon[0];
                        String eventMean = lineSplitByColon[1];
                        String eventStdDev = lineSplitByColon[2];
                        Stats newStats = new Stats(Double.parseDouble(eventMean),
                                                   Double.parseDouble(eventStdDev));
                        for (Event event : inputEvents) {
                            if (event.name.equals(eventName)) {
                                event.stats = newStats;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\n!!! Could not parse event stats data!\n\nStack trace:\n");
            e.printStackTrace();
        }
        System.out.println(" done.\n");
        
        System.out.println("Number of events loaded: " + String.valueOf(inputEvents.length));
        
        System.out.println("Checking for inconsistencies...");
        // Ensure that number of events declared match between 2 files
        if (numberOfEventsDeclaredInEventsFile != numberOfEventsDeclaredInStatsFile) {
            System.out.println("!!! Number of events declared in event data (" + String.valueOf(numberOfEventsDeclaredInEventsFile) + ") does not match the number of events declared in the event stats data (" + String.valueOf(numberOfEventsDeclaredInStatsFile) + ")!");
        }
        // Ensure that number of events match between 2 files
        if (numberOfEventsParsedInEventsFile != numberOfEventsParsedInStatsFile) {
            System.out.println("!!! Number of events parsed from the event data (" + String.valueOf(numberOfEventsParsedInEventsFile) + ") does not match the number of events parsed from the event stats data (" + String.valueOf(numberOfEventsParsedInStatsFile) + ")!");
        }
        // Ensure that number of events match the numbers specified in the files
        if (numberOfEventsDeclaredInEventsFile != numberOfEventsParsedInEventsFile) {
            System.out.println("!!! Number of events declared in the event data (" + String.valueOf(numberOfEventsDeclaredInEventsFile) + ") does not match the number of events parsed (" + String.valueOf(inputEvents.length) + ")!");
        }
        if (numberOfEventsDeclaredInStatsFile != numberOfEventsParsedInStatsFile) {
            System.out.println("!!! Number of events declared in the event stats data (" + String.valueOf(numberOfEventsDeclaredInStatsFile) + ") does not match the number of events parsed (" + String.valueOf(inputEvents.length) + ")!");
        }
        System.out.println("Consistency check complete.");
        
        // TODO: Consistency checks
        //       - Minimum must be smaller than maximum
        //       - Mean must be between minimum and maximum
        //       - Continuous minimum and maximum must be provided *
        //       - Discrete must be integer
        //       - Continous events must be 2 decimal places
        //       - All values must be positive
        
        // Generate 'baseline' data: the initial set of per-day event statistics that are considered acceptable
        System.out.println("Generating 'baseline' data...");
        ActivityEngine activityEngine = new ActivityEngine(inputEvents, numberOfDays);
        activityEngine.generateEvents();
        
        // TODO: Run analysis engine on 'baseline' data
        AnalysisEngine analysisEngine = new AnalysisEngine(activityEngine.generatedDays);
        analysisEngine.analyze();
        analysisEngine.saveStatsFile();
        
        // TODO: Run activity engine again to generate new set of data for analysis
        
        // TODO: Run alert engine on new set of data
        
        while (!isProgramGoingToQuit) {
            isProgramGoingToQuit = true;
        }
        
    }
    
}
