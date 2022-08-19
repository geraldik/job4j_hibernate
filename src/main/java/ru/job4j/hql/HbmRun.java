package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

            Candidate one = Candidate.of("Ivan", 5, 500);
            Candidate two = Candidate.of("Oleg", 7, 900);
            Candidate three = Candidate.of("Olga", 2, 300);

            create(one, sf);
            create(two, sf);
            create(three, sf);

            System.out.println(findById(1, sf));

            for (Object st : findAll(sf)) {
                System.out.println(st);
            }

            for (Object st : findByName("Ivan", sf)) {
                System.out.println(st);
            }

            update(1, "Ivan ", 6, 600, sf);
            System.out.println(findById(1, sf));

            delete(1, sf);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void create(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(candidate);
        transaction.commit();
        session.close();
    }

    public static List<Candidate> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<Candidate> candidates = session.createQuery("from Candidate").list();
        transaction.commit();
        session.close();
        return candidates;
    }

    public static Candidate findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Candidate candidate = (Candidate) session.createQuery("from Candidate c where c.id = :fId")
                                        .setParameter("fId", id)
                                        .uniqueResult();
        transaction.commit();
        session.close();
        return candidate;
    }

    public static List<Candidate> findByName(String name, SessionFactory sf) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        List<Candidate> candidates = session.createQuery("from Candidate c where c.name = :fName")
                .setParameter("fName", name)
                .list();
        transaction.commit();
        session.close();
        return candidates;
    }

    public static void update(int id, String newName, int newExperience, int newSalary, SessionFactory sf) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("update Candidate c set c.name = :newName, "
                        + "c.experience = :newExperience, c.salary = :newSalary where c.id = :fId")
                .setParameter("newName", newName)
                .setParameter("newExperience", newExperience)
                .setParameter("newSalary", newSalary)
                .setParameter("fId", id)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    public static void delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Candidate where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}