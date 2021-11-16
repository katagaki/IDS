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
        for (int i = 0; i < countOfDays; i++) {
            Day newDay = Day();
            for (event in events) {
                Event newEvent = Event("TimeOnline", event.info, Stats());
                // TODO: Do calculation
                Stats newStats = Stats(mean, stdDev);
                newEvent.stats = newStats;
                newDay.events.append(newEvent);
            }
            generatedDays.append(newDay);
        }
    }
    
}
