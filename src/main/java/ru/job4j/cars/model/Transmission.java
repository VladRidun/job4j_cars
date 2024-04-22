package ru.job4j.cars.model;

public enum Transmission {
    MECHANICAL("механическая"),
    AUTOMATIC("автоматическая"),
    ROBOT("роботизированная"),
    CVT("вариаторная"),;

    private final String displayName;

    Transmission(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
