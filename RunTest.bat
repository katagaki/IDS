@echo off
cls
javac *.java
java IDS TestData/Events.txt TestData/Stats.txt 5
