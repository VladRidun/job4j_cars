package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernateOwnerRepositoryTest {
    private static SessionFactory sf;
    private static HibernateOwnerRepository ownerRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        ownerRepository = new HibernateOwnerRepository(new CrudRepository(sf));
    }

    @AfterEach
    void cleanUp() {
        List<Owner> owners = ownerRepository.findAll();
        for (Owner owner : owners) {
            ownerRepository.delete(owner.getId());
        }
    }

    @Test
    void createIsDone() {
        var owner = Owner.of()
                .name("Vlad")
                .build();
        var addedOwner = ownerRepository.create(owner);
        assertThat(addedOwner.getName()).isEqualTo(owner.getName());
    }

    @Test
    void findByIdIsDone() {
        var owner1 = Owner.of()
                .name("Vlad")
                .build();
        var addedOwner = ownerRepository.create(owner1);
        var owner2 = Owner.of()
                .name("Dima")
                .build();
        ownerRepository.create(owner2);
        assertThat(owner1.getName())
                .isEqualTo(ownerRepository.findById(addedOwner
                                .getId())
                        .get()
                        .getName());
    }

    @Test
    void updateIsDone() {
        var owner1 = Owner.of()
                .name("Vlad")
                .build();
        var addedOwner = ownerRepository.create(owner1);
        var owner2 = Owner.of()
                .name("Dima")
                .build();
        ownerRepository.create(owner2);
        var owner3 = Owner.of()
                .id(addedOwner.getId())
                .name("Aleks")
                .build();
        ownerRepository.update(owner3);
        assertThat(owner3.getName())
                .isEqualTo(ownerRepository.findById(addedOwner
                                .getId())
                        .get()
                        .getName());
    }

    @Test
    void deleteIsDone() {
        var owner1 = Owner.of()
                .name("Vlad")
                .build();
        var addedOwner = ownerRepository.create(owner1);
        ownerRepository.delete(addedOwner.getId());
        assertThat(ownerRepository.findById(addedOwner
                .getId()))
                .isEmpty();
    }

    @Test
    void findAll() {
        var owner1 = Owner.of()
                .name("Vlad")
                .build();
        ownerRepository.create(owner1);
        var owner2 = Owner.of()
                .name("Dima")
                .build();
        ownerRepository.create(owner2);
        var owner3 = Owner.of()
                .name("Aleks")
                .build();
        ownerRepository.create(owner3);
        List<String> names = List.of(owner1.getName(), owner2.getName(), owner3.getName());
        List<String> addedNames = new ArrayList<>();
        for (Owner owner : ownerRepository.findAll()) {
            addedNames.add(owner.getName());
        }
        assertThat(names).isEqualTo(addedNames);
    }
}