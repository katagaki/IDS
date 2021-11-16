import java.util.Scanner;

// The main class for the program

class IDS {
    
    public static void main(String[] args) {
        Boolean isProgramGoingToQuit = false;
        String eventsFileName = args[0];
        String statsFileName = args[1];
        String numberOfDays = args[2];
        
        System.out.println("Loading initial input...");
        // TODO: Read initial input
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
