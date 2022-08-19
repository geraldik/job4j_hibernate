package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Candidate;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Ivan", 5, 500);
            Candidate two = Candidate.of("Oleg", 7, 900);
            Candidate three = Candidate.of("Olga", 2, 300);

            session.save(one);
            session.save(two);
            session.save(three);

            System.out.println(findById(1, session));

            for (Object st : findAll(session)) {
                System.out.println(st);
            }

            for (Object st : findByName("Ivan", session)) {
                System.out.println(st);
            }

            update(1, "Ivan ", 6, 600, session);
            System.out.println(findById(1, session));

            delete(1, session);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static List<Candidate> findAll(Session session) {
        Query query = session.createQuery("from Candidate");
        return query.list();
    }

    public static Candidate findById(int id, Session session) {
        Query<Candidate> query = session.createQuery("from Candidate c where c.id = :fId");
        query.setParameter("fId", id);
        return query.uniqueResult();
    }

    public static List<Candidate> findByName(String name, Session session) {
        Query<Candidate> query = session.createQuery("from Candidate c where c.name = :fName")
                .setParameter("fName", name);
        return query.list();
    }

    public static void update(int id, String newName, int newExperience, int newSalary, Session session) {
        session.createQuery("update Candidate c set c.name = :newName, "
                        + "c.experience = :newExperience, c.salary = :newSalary where c.id = :fId")
                .setParameter("newName", newName)
                .setParameter("newExperience", newExperience)
                .setParameter("newSalary", newSalary)
                .setParameter("fId", id)
                .executeUpdate();
    }

    public static void delete(int id, Session session) {
        session.createQuery("delete from Candidate where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
    }
}