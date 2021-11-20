class ActivityEngine {
    
    public int countOfDays = 0;
    public Event[] inputEvents = [];
    public Day[] generatedDays = [];
    
    public ActivityEngine(Event[] inputEvents, Stats[] inputStats, int countOfDays) {
        this.inputEvents = inputEvents;
        this.inputStats = inputStats;
        this.countOfDays = countOfDays;
    }
    
    public void generateEvents() {
        // Process each day
        for (int i = 0; i < countOfDays; i++) {
            Day newDay = Day();
            
            // Process each event for each day
            for (event in events) {
                // Create event modelled after existing event
                Event newEvent = Event(event.name, event.info, event.stats);
                newDay.events.append(newEvent);
            }
            
            generatedDays.append(newDay);
        }
    }
    
}
