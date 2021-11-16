class Stats {
    
    public Double mean;
    public Double stdDev;
    
    public Stats() {
        Event(0, 0);
    }
    
    public Stats(Double mean, Double stdDev) {
        this.mean = mean;
        this.stdDev = stdDev;
    }
    
}
