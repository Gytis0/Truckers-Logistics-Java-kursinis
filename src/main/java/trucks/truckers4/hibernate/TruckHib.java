package trucks.truckers4.hibernate;

import trucks.truckers4.Model.Destination;
import trucks.truckers4.Model.Driver;
import trucks.truckers4.Model.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class TruckHib {

    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public TruckHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createTruck(Truck truck){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(truck);
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void updateTruck(Truck truck){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(truck);
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void deleteTruck(Truck truck){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Truck.class, truck.getId()));
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public List<Truck> getAllTrucks() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Truck> truckQuery = cb.createQuery(Truck.class);
            truckQuery.select(truckQuery.from(Truck.class));
            Query q = entityManager.createQuery(truckQuery);
            return q.getResultList();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if (entityManager != null){
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }
}
