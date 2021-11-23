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
        Event[] inputEvents = {};
        String consistencyCheckReport = "";
        String[] eventsLines = new String[]{};
        String[] statsLines = new String[]{};
        
        clearScreen();
        
        // TODO: Validate command line arguments
        
        System.out.print("Loading initial event data...");
        try {
            eventsLines = new FileArrayProvider().readLines(Paths.get(eventsFileName).normalize().toString());
            inputEvents = new Event[eventsLines.length - 1];
            for (int i = 1; i < eventsLines.length; i++) {
                String[] lineSplitByColon = eventsLines[i].split(":");
                if (lineSplitByColon.length == 5) {
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
        } catch (IOException e) {
            System.out.println("\n!!! Could not parse event data!\n\nStack trace:\n");
            e.printStackTrace();
        }
        System.out.println(" done.");
        
        System.out.print("Loading initial event stats data...");
        statsLines = loadStats(inputEvents, statsFileName);
        System.out.println(" done.\n");
        
        System.out.println("Number of events loaded: " + String.valueOf(inputEvents.length) + "\n");
        
        // Check consistency between events and event stats file
        System.out.println("Checking for inconsistencies...");
        consistencyCheckReport = checkConsistency(eventsLines, statsLines);
        if (consistencyCheckReport.equals("")) {
            System.out.println("Consistency check complete. No inconsistencies found.");
        } else {
            System.out.println("Consistency check failed. Inconsistencies found:\n\n" + consistencyCheckReport);
        }
        System.out.println();
        
        // Generate 'baseline' data: the initial set of per-day event statistics that are considered acceptable
        System.out.println("Generating 'baseline' data...");
        ActivityEngine primaryActivityEngine = new ActivityEngine(inputEvents, numberOfDays);
        primaryActivityEngine.generateEvents();
        primaryActivityEngine.saveDayFiles();
        System.out.println();
        
        // Run analysis engine on 'baseline' data
        System.out.println("Generating analyzed data based on 'baseline' data...");
        AnalysisEngine primaryAnalysisEngine = new AnalysisEngine(primaryActivityEngine.generatedDays);
        primaryAnalysisEngine.analyze();
        primaryAnalysisEngine.saveStatsFile();
        System.out.println();
        
        // TODO: Use a loop to keep 'training' the IDS
        while (!isProgramGoingToQuit) {
            Scanner s = new Scanner(System.in);
            String newStatsFileName = "";
            int newNumberOfDays = -1;
            
            // Read in new stats file name
            while (newStatsFileName.equals("")) {
                System.out.print("Enter the name of the file containing the next set of event stats data: ");
                newStatsFileName = s.nextLine();
            }
            
            // Read the new number of days
            while (newNumberOfDays == -1) {
                System.out.print("Enter the number of days of activity to simulate: ");
                newNumberOfDays = s.nextInt();
            }
            System.out.println();
            
            // Clear previous set of stats
            for (Event event : inputEvents) {
                event.stats = new Stats();
            }
            
            System.out.print("Loading new event stats data...");
            statsLines = loadStats(inputEvents, newStatsFileName);
            System.out.println(" done.\n");
            
            // Check consistency between events and event stats file
            System.out.println("Checking for inconsistencies...");
            consistencyCheckReport = checkConsistency(eventsLines, statsLines);
            if (consistencyCheckReport.equals("")) {
                System.out.println("Consistency check complete. No inconsistencies found.");
            } else {
                System.out.println("Consistency check failed. Inconsistencies found:\n\n" + consistencyCheckReport);
            }
            
            // Run activity engine again to generate new set of data for analysis
            ActivityEngine secondaryActivityEngine = new ActivityEngine(inputEvents, newNumberOfDays);
            secondaryActivityEngine.generateEvents();
            
            // Run analysis engine on new set of generated activity data
            System.out.println("Generating analyzed data based on new 'live' data...");
            AnalysisEngine secondaryAnalysisEngine = new AnalysisEngine(secondaryActivityEngine.generatedDays);
            secondaryAnalysisEngine.analyze();
            secondaryAnalysisEngine.saveStatsFile();
            System.out.println();
            
            // Run alert engine on new set of data
            AlertEngine alertEngine = new AlertEngine(inputEvents,
                                                      secondaryActivityEngine.generatedDays,
                                                      primaryAnalysisEngine.averages,
                                                      primaryAnalysisEngine.stdDevs);
            alertEngine.genDailyCount();
            System.out.println("Threshold for alarm: " + Integer.toString(alertEngine.threshold));
            alertEngine.detectAnomaly();
            
            System.out.print("Continue analyzing another set of data? (Y/N) ");
            
            String input = s.next();
            if (!input.equalsIgnoreCase("Y")) {
                isProgramGoingToQuit = true;
            }
        }
        
    }
    
    public static String checkConsistency(String[] eventsLines, String[] statsLines) {
        String inconsistencies = "";
        int numberOfEventsDeclaredInEventsFile = Integer.parseInt(eventsLines[0]);
        int numberOfEventsDeclaredInStatsFile = Integer.parseInt(statsLines[0]);
        int numberOfEventsParsedInEventsFile = 0;
        int numberOfEventsParsedInStatsFile = 0;
        
        // Get the number of parsable events in events file
        for (int i = 1; i < eventsLines.length; i++) {
            String line = eventsLines[i];
            String[] lineSplitByColon = line.split(":");
            if (lineSplitByColon.length == 5) {
                numberOfEventsParsedInEventsFile += 1;
            }
        }
        
        // Get the number of parsable events in stats file
        for (int i = 1; i < statsLines.length; i++) {
            String line = statsLines[i];
            String[] lineSplitByColon = line.split(":");
            if (lineSplitByColon.length == 3) {
                numberOfEventsParsedInStatsFile += 1;
            }
        }
        
        // Ensure that number of events declared match between 2 files
        if (numberOfEventsDeclaredInEventsFile != numberOfEventsDeclaredInStatsFile) {
            inconsistencies = inconsistencies + ("!!! Number of events declared in event data (" + String.valueOf(numberOfEventsDeclaredInEventsFile) + ") does not match the number of events declared in the event stats data (" + String.valueOf(numberOfEventsDeclaredInStatsFile) + ")!\n");
        }
        // Ensure that number of events match between 2 files
        if (numberOfEventsParsedInEventsFile != numberOfEventsParsedInStatsFile) {
            inconsistencies = inconsistencies + ("!!! Number of events parsed from the event data (" + String.valueOf(numberOfEventsParsedInEventsFile) + ") does not match the number of events parsed from the event stats data (" + String.valueOf(numberOfEventsParsedInStatsFile) + ")!\n");
        }
        // Ensure that number of events match the numbers specified in the files
        if (numberOfEventsDeclaredInEventsFile != numberOfEventsParsedInEventsFile) {
            inconsistencies = inconsistencies + ("!!! Number of events declared in the event data (" + String.valueOf(numberOfEventsDeclaredInEventsFile) + ") does not match the number of events parsed (" + String.valueOf(numberOfEventsParsedInEventsFile) + ")!\n");
        }
        if (numberOfEventsDeclaredInStatsFile != numberOfEventsParsedInStatsFile) {
            inconsistencies = inconsistencies + ("!!! Number of events declared in the event stats data (" + String.valueOf(numberOfEventsDeclaredInStatsFile) + ") does not match the number of events parsed (" + String.valueOf(numberOfEventsParsedInStatsFile) + ")!\n");
        }
        
        // TODO: Other consistency checks
        //       - Minimum must be smaller than maximum
        //       - Mean must be between minimum and maximum
        //       - Continuous minimum and maximum must be provided *
        //       - Discrete must be integer
        //       - Continous events must be 2 decimal places
        //       - All values must be positive
        
        return inconsistencies.trim();
    }
    
    public static String[] loadStats(Event[] events, String statsFileName) {
        String[] statsLines = new String[]{};
        try {
            statsLines = new FileArrayProvider().readLines(Paths.get(statsFileName).normalize().toString());
            for (String line : statsLines) {
                // Parse line split by colons
                String[] lineSplitByColon = line.split(":");
                if (lineSplitByColon.length == 3) {
                    String eventName = lineSplitByColon[0];
                    String eventMean = lineSplitByColon[1];
                    String eventStdDev = lineSplitByColon[2];
                    Stats newStats = new Stats(Double.parseDouble(eventMean),
                                               Double.parseDouble(eventStdDev));
                    for (Event event : events) {
                        if (event.name.equals(eventName)) {
                            event.stats = newStats;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\n!!! Could not parse event stats data!\n\nStack trace:\n");
            e.printStackTrace();
        }
        return statsLines;
    }
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
    
}
