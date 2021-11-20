import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Event {
    
    private SecureRandom rand;
    public String name;
    public EventInfo info;
    public int intValue = 0;
    public Double doubleValue = 0.0;
    public Stats stats;
    
    public Event() {
        this("", new EventInfo(), new Stats());
    }
    
    public Event(String name, EventInfo info, Stats stats) {
        this.name = name;
        this.info = info;
        this.stats = stats;
        try {
            rand = SecureRandom.getInstance("NativePRNG");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("!!! This device does not support secure random number generation!");
        }
    }
    
    public void generate() {
        switch (info.eventType) {
            case Discrete:
                intValue = generateDiscrete();
                break;
            case Continuous:
                doubleValue = generateContinuous();
                break;
        }
    }
    
    private int generateDiscrete() {
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
    
    private Double generateContinuous() {
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
    
    @Override
    public String toString() {
        return name.substring(0, 8) + "\t" + String.valueOf(info.min) + "\t" + String.valueOf(info.max) + "\t" + String.valueOf(info.weight) + "\t" + String.valueOf(stats.mean) + "\t" + String.valueOf(stats.stdDev);
    }
    
}
