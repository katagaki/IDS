class Day {
    
    public Event[] events;
    
    public Day() {
        events = [];
    }
    
    public String outputString() {
        String output = "";
        
        for (event in events) {
            output = output + event.name + ":";
            swich (event.info.eventType) {
                case EventType.Discrete:
                    output = output + Integer.toString(event.intValue);
                    break;
                case EventType.Continous:
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
