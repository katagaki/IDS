# IDS
## Intrusion detection analysis in Java.

**CAUTION: This code is provided AS-IS and should be considered an archive for reference purposes only. The code may contain errors or bugs that may not be fixed.**

## Stats.txt

### Format
Days
EventName:Mean:StandardDeviation

## Events.txt

### Format
Days
EventName:Continuous/Discrete:MinimumAllowed:MaximumAllowed:WeightOfAnomalyCount

## Activity Engine
    
    // Must take in input: events list, stats list, number of days to generate
    //
    // Initial data takes in number of days from command line argument, programmatically pass to Activity Engine
    // Subsequent runs will take in number of days and stats from user input
    //
    // After generation, save events in separate day files, and save  the new statistics in a new stats file

### Generate Discrete Event

    min = if(exists(event.min)) { event.min } else { 0 }
    max = if(exists(event.max)) { event.max } else { event.StandardDeviation * 4 }
    return int(randBetween(min, max))

### Generate Continuous Event
    
    x = randBetween(event.min, event.max)
    y = randBetween(0, 1)
    if (x > event.mean) {
        if (y < event.skewFactor) {
            return x
        } else {
            return x * event.skewFactor
        }
    } else {
        return x
    }

### Calculate Skew Factor (event.skewFactor)
    
    return 0.9 / ((event.max - event.min) / event.mean)

## Analysis Engine
    
    // Calculate analysis of event stat in day
    dailyTotal = sum(days[day].allEventData) // Sum of all the event data in each day
    avg = sum(event.allData) / days.count
    stdDev = stdDev(event.allData)
    return abs(avg - event[day].data) / stdDev * event[day].weight

## Alert Engine
    
    // Read in new stats file
    // Read in number of days to generate live data
    // Run Activity Engine against new stats file and new number of days
    // Run Analysis Engine against newly generated 'live' data
    
### Calculate threshold
    
    return sum(event.allWeights)
    
### Calculate anomaly counter for 'live' data and compare
    
    for event in events {
        if event.anomalous() {
            anomalyCounter += event.weight * event.deviation
        }
    }

### Check if simulated event is anomalous

    if anomalyCounter > sum(event.weight) {
        isAnomalyDetected = true
    } else {
        isAnomalyDetected = false
    }
