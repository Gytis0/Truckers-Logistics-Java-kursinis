package trucks.truckers4.hibernate;

import trucks.truckers4.Model.Checkpoint;
import trucks.truckers4.Model.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CheckpointHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public CheckpointHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createCheckpoint(Checkpoint checkpoint){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(checkpoint);
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void updateCheckpoint(Checkpoint checkpoint){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(checkpoint);
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }


    public void deleteCheckpoint(Checkpoint checkpoint){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Checkpoint.class, checkpoint.getId()));
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public List<Checkpoint> getAllCheckpoints() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Checkpoint> checkpointQuery = cb.createQuery(Checkpoint.class);
            checkpointQuery.select(checkpointQuery.from(Checkpoint.class));
            Query q = entityManager.createQuery(checkpointQuery);
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
