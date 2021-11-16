class ActivityEngine {
    
    public int countOfDays = 0;
    public Event[] inputEvents = [];
    public Event[] generatedEvents = [];
    
    public ActivityEngine(Event[] inputEvents, Stats[] inputStats, int countOfDays) {
        this.inputEvents = inputEvents;
        this.inputStats = inputStats;
        this.countOfDays = countOfDays;
    }
    
    public void generateEvents() {
        Event[] newEvents = [];
        for (event in events) {
            Event newEvent = Event("TimeOnline", event.info, Stats());
            // TODO: Do calculation
            Stats newStats = Stats(mean, stdDev);
            newEvent.stats = newStats;
            newEvents.append(newEvent);
        }
        generatedEvents = newEvents;
    }
    
}
