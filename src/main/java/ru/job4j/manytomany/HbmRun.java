package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Author;
import ru.job4j.model.Book;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of("Преступление и наказание");
            Book two = Book.of("Двенадцать стульев");
            Book three = Book.of("Скотный двор");
            Book four = Book.of("1984");

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);

            Author first = Author.of("Достоевский Ф.М.");
            first.getBooks().add(one);

            Author second = Author.of("Илья И.");
            second.getBooks().add(two);

            Author third = Author.of("Евгений П.");
            third.getBooks().add(two);

            Author fourth = Author.of("Джордж О.");
            fourth.getBooks().add(three);
            fourth.getBooks().add(four);

            session.save(first);
            session.save(second);
            session.save(third);
            session.save(fourth);

            Author author = session.get(Author.class, 2);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}