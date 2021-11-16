import java.util.Random;

class Event {
    
    public String name;
    public EventInfo info;
    public int intValue;
    public Double doubleValue;
    public Stats stats;
    
    public Event() {
        Event("", EventInfo(), Stats());
    }
    
    public Event(String name, EventInfo info, Stats stats) {
        this.name = name;
        this.info = info;
        this.stats = stats;
        switch (info.eventType) {
            case EventType.Discrete:
                intValue = generateDiscrete();
                break;
            case EventType.Continuous:
                doubleValue = generateContinuous();
                break;
        }
    }
    
    public int generateDiscrete() {
        Random rand = new Random(System.currentTimeMillis());
        int min = 0;
        int max = 0;
        if (info.minExists) {
            min = info.min;
        }
        if (info.maxExists) {
            max = info.max;
        } else {
            max = stats.stdDev * 4;
        }
        intValue = rand.ints(min, max + 1).findFirst().getAsInt();
    }
    
    public Double generateContinuous() {
        Random rand = new Random(System.currentTimeMillis());
        int x = rand.nextInt(info.max - info.min) + info.min;
        Double y = (rand.nextInt(1001) - 1000) / 1000.0;
        if (x > stats.mean) {
            Double skewFactor = calculateSkewFactor();
            if (y < skewFactor) {
                doubleValue = x;
            } else {
                doubleValue = x * skewFactor;
            }
        } else {
            return x;
        }
    }
    
    private Double calculateSkewFactor() {
        return 0.9 / ((info.max - info.min) / stats.mean);
    }
    
}
