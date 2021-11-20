public class Stats {
    
    public Double mean;
    public Double stdDev;
    
    public Stats() {
        this(0.0, 0.0);
    }
    
    public Stats(Double mean, Double stdDev) {
        this.mean = mean;
        this.stdDev = stdDev;
    }
    
}
