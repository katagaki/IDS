@echo off
cls
del *.class
javac *.java
java IDS TestData/Events.txt TestData/Stats.txt 5
