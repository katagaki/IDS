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

## Sample Code

    for event in events {
        if event.anomalous() {
            anomalyCounter += event.weight * event.deviation
        }
    }
    if anomalyCounter > sum(event.weight) {
        isAnomalyDetected = true
    } else {
        isAnomalyDetected = false
    }
