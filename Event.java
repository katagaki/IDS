import java.util.Random;

public class Event {
    
    public String name;
    public EventInfo info;
    public int intValue;
    public Double doubleValue;
    public Stats stats;
    
    public Event() {
        this("", new EventInfo(), new Stats());
    }
    
    public Event(String name, EventInfo info, Stats stats) {
        this.name = name;
        this.info = info;
        this.stats = stats;
        switch (info.eventType) {
            case Discrete:
                intValue = generateDiscrete();
                break;
            case Continuous:
                doubleValue = generateContinuous();
                break;
        }
    }
    
    public int generateDiscrete() {
        Random rand = new Random(System.currentTimeMillis());
        Double min = 0.0;
        Double max = 0.0;
        if (info.minExists) {
            min = info.min;
        }
        if (info.maxExists) {
            max = info.max;
        } else {
            max = stats.stdDev * 4;
        }
        return rand.ints(min.intValue(), max.intValue() + 1).findFirst().getAsInt();
    }
    
    public Double generateContinuous() {
        Random rand = new Random(System.currentTimeMillis());
        Double x = rand.nextDouble(info.max - info.min) + info.min;
        Double y = (rand.nextInt(1001) - 1000) / 1000.0;
        if (x > stats.mean) {
            Double skewFactor = calculateSkewFactor();
            if (y < skewFactor) {
                return x;
            } else {
                return x * skewFactor;
            }
        } else {
            return x;
        }
    }
    
    private Double calculateSkewFactor() {
        Double probability = 1.0 / Math.ceil(info.max / stats.mean);
        return 1.0 - probability;
    }
    
}
