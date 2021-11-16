class ActivityEngine {
    
    // Input
    private Event[] inputEvents = [];
    private Stats[] inputStats = [];
    
    // Output
    public int countOfDays = 0;
    public Event[] events = [];
    public Stats[] stats = [];
    
    public ActivityEngine(Event[] inputEvents, Stats[] inputStats, int countOfDays) {
        this.inputEvents = inputEvents;
        this.inputStats = inputStats;
        this.countOfDays = countOfDays;
    }
    
    public void generateEvents() {
        
    }
    
}
