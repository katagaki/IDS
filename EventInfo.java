public class EventInfo {
    
    public EventType eventType;
    public Double min;
    public Double max;
    public Boolean minExists;
    public Boolean maxExists;
    public int weight = 1;
    
    public EventInfo() {
        this(EventType.Discrete, 0.0, 0.0, false, false, 1);
    }
    
    public EventInfo(EventType eventType,
                     Double min,
                     Double max,
                     Boolean minExists,
                     Boolean maxExists,
                     int weight) {
        this.eventType = eventType;
        this.min = min;
        this.max = max;
        this.minExists = minExists;
        this.maxExists = maxExists;
        this.weight = weight;
    }
    
}
