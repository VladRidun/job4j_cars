package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import static org.assertj.core.api.Assertions.*;

class HibernateEngineRepositoryTest {
    private static SessionFactory sf;
    private static HibernateEngineRepository engineRepository;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate_test.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        engineRepository = new HibernateEngineRepository(new CrudRepository(sf));
    }

    @Test
    void createIsDone() {
        var engine = Engine
                .of()
                .name("Gasoline")
                .build();
        var addedEngine = engineRepository.create(engine);
        assertThat(addedEngine.getName()).isEqualTo(engine.getName());
    }

    @Test
    void findByIdIsDone() {
        var engine = Engine
                .of()
                .name("Gasoline")
                .build();
        var addedEngine = engineRepository.create(engine);
        assertThat(addedEngine.getName()).isEqualTo(engine.getName());
    }

    @Test
    void updateIsDone() {
        var engine1 = Engine
                .of()
                .name("Gasoline")
                .build();
        engineRepository.create(engine1);
        var engine2 = Engine
                .of()
                .name("Diesel")
                .build();
        var addedEngine2 = engineRepository.create(engine2);
        var engine3 = Engine
                .of()
                .id(addedEngine2.getId())
                .name("Metan")
                .build();
        engineRepository.update(engine3);
        var updatedEngineOptional = engineRepository.findById(addedEngine2.getId());
        assertThat(updatedEngineOptional.get().getName()).isEqualTo(engine3.getName());
    }

    @Test
    void deleteIsDone() {
        var engine1 = Engine
                .of()
                .name("Gasoline")
                .build();
        var engine2 = Engine
                .of()
                .name("Diesel")
                .build();
        var addedEngine = engineRepository.create(engine1);
        var addedEngine2 = engineRepository.create(engine2);
        engineRepository.delete(addedEngine.getId());
        assertThat(engineRepository.findById(addedEngine.getId())).isEmpty();
        assertThat(engineRepository.findById(addedEngine2
                .getId())
                .get()
                .getName())
                .isEqualTo(engine2.getName());
    }
}

