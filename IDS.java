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
                    // TODO: Read each line
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
