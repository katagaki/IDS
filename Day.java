import java.text.DecimalFormat;

public class Day {
    
    public Event[] events = {};
    
    public Day() { }
    
    @Override
    public String toString() {
        String output = "";
        
        for (Event event : events) {
            output = output + event.name + ":";
            switch (event.info.eventType) {
                case Discrete:
                    output = output + Integer.toString(event.intValue);
                    break;
                case Continuous:
                    DecimalFormat df = new DecimalFormat("#.00");
                    String doubleFormatted = df.format(event.doubleValue);
                    output = output + doubleFormatted;
                    break;
            }
            output = output + "\n";
        }
        
        return output;
    }
    
}
