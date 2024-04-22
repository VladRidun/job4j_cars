package ru.job4j.cars.model;

public enum Colour {
    RED("красный"),
    YELLOW("желтый"),
    GREEN("зеленый"),
    LIGHT_BLUE("голубой"),
    DARK_BLUE("синий"),
    BROWN("коричневый"),
    WHITE("белый"),
    ORANGE("оранжевый"),
    PINK("розовый"),
    BLACK("черный"),
    GRAY("серый");

    private final String displayName;

    Colour(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
