package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "auto_post")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Integer id;
    private String description;
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    private Integer price;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "auto_photo_id")
    private List<AutoPhoto> autoPhotos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistories = new ArrayList<>();

}