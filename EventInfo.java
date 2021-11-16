class EventInfo {
    
    public EventType eventType;
    public Double min;
    public Double max;
    public Boolean minExists;
    public Boolean maxExists;
    public int weight;
    
    public EventInfo() {
        Event(EventType.Discrete, 0, 0, 0);
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

enum EventType {
    Discrete,
    Continuous
}
