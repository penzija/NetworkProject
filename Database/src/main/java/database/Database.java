package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class Database {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    public List<Person> showAll() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = q.getResultList();

        em.close();
        return persons;
    }

}
