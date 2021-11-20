public class ActivityEngine {
    
    public int countOfDays;
    public Event[] inputEvents;
    public Day[] generatedDays;
    
    public ActivityEngine(Event[] inputEvents, int countOfDays) {
        this.inputEvents = inputEvents;
        this.countOfDays = countOfDays;
        generatedDays = new Day[countOfDays];
    }
    
    public void generateEvents() {
        // Process each day
        for (int i = 0; i < countOfDays; i++) {
            Day newDay = new Day();
            newDay.events = new Event[inputEvents.length];
            
            // Process each event for each day
            for (int j = 0; j < inputEvents.length; j++) {
                // Create event modelled after existing event
                Event event = inputEvents[j];
                Event newEvent = new Event(event.name, event.info, event.stats);
                newEvent.generate();
                newDay.events[j] = newEvent;
            }
            
            generatedDays[i] = newDay;
        }
    }
    
}
