package ru.job4j.cars.model;

public enum Engine {
    GASOLINE("Бензин"),
    DIESEL("Дизель"),
    ELECTRIC("Электрический");

    private final String displayName;

    Engine(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
