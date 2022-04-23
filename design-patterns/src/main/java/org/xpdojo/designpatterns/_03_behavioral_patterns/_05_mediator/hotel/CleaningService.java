package org.xpdojo.designpatterns._03_behavioral_patterns._05_mediator.hotel;

public class CleaningService {

    public String getTowers(String roomNumber, int numberOfTowers) {
        return "provide " + numberOfTowers + " towers to " + roomNumber;
    }
}
