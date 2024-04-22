package ru.job4j.cars.model;

import lombok.*;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "car")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "model_id")
    private Model model;

    private String vinNumber;
    private Long mileage;
    private int yearProduction;

    @Enumerated(EnumType.STRING)
    private Colour colour;

    @Enumerated(EnumType.STRING)
    private Engine engine;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

}