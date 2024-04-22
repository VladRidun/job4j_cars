package ru.job4j.cars.model;

public enum BodyType {
    SEDAN("седан"),
    COUPE("купе"),
    HATCHBACK("хэтчбек"),
    LIFTBACK("лифтбек"),
    FASTBACK("фастбэк"),
    UNIVERSAL("универсал"),
    CROSSOVER("кроссовер"),
    PICKUP("пикап"),
    MINIVAN("минивэн"),
    CABROILET("кабриолет"),
    LIMUZIN("лимузин");

    private final String displayName;

    BodyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
