// The main class for the program

class SusDetector {

    public static void main(String[] args) {
        clearScreen();
        System.out.println("Welcome to the SusDetector Intrusion Detection System.");
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

}
