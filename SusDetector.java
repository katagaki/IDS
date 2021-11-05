import java.util.Scanner;

// The main class for the program

class SusDetector {
    
    public static void main(String[] args) {
        Boolean isProgramGoingToQuit = false;
        int selection = -1;
        
        clearScreen();
        System.out.println("Loading initial input...");
        // TODO: Do initial input
        
        while (!isProgramGoingToQuit) {
            selection = printMenu();
            switch (selection) {
            case 1:
                // TODO: Implement activity simulation engine
                break;
                
            case 2:
                // TODO: Implement analysis engine
                break;
                
            case 3:
                // TODO: Implement alert engine
                break;
                
            case 4:
                isProgramGoingToQuit = true;
                break;
            }
        }
        
        clearScreen();
        System.out.println("Thank you for using SusDetector!");
        System.out.println();
    }
    
    private static int printMenu() {
        Scanner s = new Scanner(System.in);
        Boolean isValidSelectionMade = false;
        Boolean isPreviousSelectionInvalid = false;
        int selection = -1;
        
        while (!isValidSelectionMade) {
            clearScreen();
            System.out.println("Welcome to the SusDetector Intrusion Detection System.");
            System.out.println();
            System.out.println("Select a module to continue.");
            System.out.println();
            System.out.println(" +---+----------------------------+");
            System.out.println(" | 1 | Activity Simulation Engine |");
            System.out.println(" +---+----------------------------+");
            System.out.println(" | 2 | Analysis Engine            |");
            System.out.println(" +---+----------------------------+");
            System.out.println(" | 3 | Alert Engine               |");
            System.out.println(" +---+----------------------------+");
            System.out.println(" | 4 | Quit                       |");
            System.out.println(" +---+----------------------------+");
            System.out.println();
            if (isPreviousSelectionInvalid) {
                System.out.println("Invalid selection, please try again.");
                System.out.println();
            }
            System.out.print("Your selection: ");
            
            if (s.hasNextInt()) {
                selection = s.nextInt();
            } else {
                s.nextLine();
            }
            
            if (selection >= 1 && selection <= 4) {
                isValidSelectionMade = true;
            } else {
                isPreviousSelectionInvalid = true;
            }
        }
        
        return selection;
    }   
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
    
}
