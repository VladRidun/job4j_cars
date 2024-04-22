package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateBrandRepositoryTest {
    private static SessionFactory sf;
    private static HibernateBrandRepository brandRepository;

    @BeforeEach
    public void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        brandRepository = new HibernateBrandRepository((new CrudRepository(sf)));
    }

    @AfterEach
    public void clean() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Brand")
                    .executeUpdate();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    void whenCreateBrandAndGetSame() {
        var brand = new Brand(1L, "Volvo");
        var brandAdded = brandRepository.create(brand);
        assertThat(brandAdded.getName()).isEqualTo(brand.getName());
    }

    @Test
    void whenFindByIdTrue() {
        var brandAdded = brandRepository.create(new Brand(1L, "Volvo"));
        var brandOptional = brandRepository.findById(1L);
        assertThat(brandOptional.get().getName()).isEqualTo(brandAdded.getName());
    }

    @Test
    void whenUpdateAndGetSameUpdated() {
        var brand = new Brand(1L, "uaz");
        var newBrand = new Brand(1L, "GAZ");
        brandRepository.create(brand);
        brandRepository.update(newBrand);
        var brandOptional = brandRepository.findById(1L);
        assertThat(brandOptional.get().getName()).isEqualTo(newBrand.getName());
    }

    @Test
    void whenDeleteIsTrue() {
        var brand = new Brand(1L, "Volvo");
        var brandAdded = brandRepository.create(brand);
        brandRepository.delete(1L);
        var brandOptional = brandRepository.findById(1L);
        assertThat(brandOptional.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllIsTrue() {
        var volvo = new Brand(1L, "Volvo");
        var gaz = new Brand(2L, "GAZ");
        List<Brand> brandListAdd = List.of(volvo, gaz);
        brandRepository.create(volvo);
        brandRepository.create(gaz);
        List<Brand> brandListGet = brandRepository.findAll();
        assertThat(brandListAdd).isEqualTo(brandListGet);
    }
}