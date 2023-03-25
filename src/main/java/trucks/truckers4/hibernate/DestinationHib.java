package trucks.truckers4.hibernate;

import trucks.truckers4.Model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DestinationHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;
    UserHib userHib;

    public DestinationHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
    }

    public void createDestination(Destination dest) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(dest);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void updateDestination(Destination dest) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(dest);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void deleteDestination(Destination dest) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Destination.class, dest.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public List<Destination> getAllDestinations() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Destination> destinationQuery = cb.createQuery(Destination.class);
            destinationQuery.select(destinationQuery.from(Destination.class));
            Query q = entityManager.createQuery(destinationQuery);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
        return new ArrayList<>();
    }

    public Destination getDestinationById(int id) {
        entityManager = entityManagerFactory.createEntityManager();
        Query q = null;
        CriteriaQuery<Destination> destinationQuery = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        destinationQuery = cb.createQuery(Destination.class);
        Root<Destination> destinationRoot = destinationQuery.from(Destination.class);
        destinationQuery.select(destinationRoot).where(cb.like(destinationRoot.get("id"), String.valueOf(id)));

        try {
            q = entityManager.createQuery(destinationQuery);
            return (Destination) q.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
        return null;
    }
}