package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.CarBrand;
import ru.job4j.model.CarModel;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarModel one = CarModel.of("Rav 4");
            CarModel two = CarModel.of("Rav 4");
            CarModel three = CarModel.of("Rav 4");
            CarModel four = CarModel.of("Rav 4");
            CarModel five = CarModel.of("Rav 4");
            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            CarBrand brand = CarBrand.of("Toyota");
            brand.addModel(session.load(CarModel.class, 1));
            brand.addModel(session.load(CarModel.class, 2));
            brand.addModel(session.load(CarModel.class, 3));
            brand.addModel(session.load(CarModel.class, 4));
            brand.addModel(session.load(CarModel.class, 5));

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}