import java.text.DecimalFormat;

public class Day {
    
    public Event[] events = {};
    
    public Day() { }
    
    @Override
    public String toString() {
        String output = "";
        
        for (Event event : events) {
            DecimalFormat df = new DecimalFormat("0.00");
            output = output + event.name + ":";
            switch (event.info.eventType) {
                case Discrete:
                    output = output + Integer.toString(event.intValue) + ":";
                    break;
                case Continuous:
                    output = output + df.format(event.doubleValue) + ":";
                    break;
            }
            output = output + df.format(event.analyzedValue) + ":\n";
        }
        
        return output;
    }
    
}
