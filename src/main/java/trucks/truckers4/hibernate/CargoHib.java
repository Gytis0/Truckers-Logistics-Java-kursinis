package trucks.truckers4.hibernate;

import trucks.truckers4.Model.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CargoHib {

    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public CargoHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createCargo(Cargo cargo){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void updateCargo(Cargo cargo){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }

    }

    public void deleteCargo(Cargo cargo){
        entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Cargo.class, cargo.getId()));
            entityManager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public List<Cargo> getAllCargos() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cargo> cargoQuery = cb.createQuery(Cargo.class);
            cargoQuery.select(cargoQuery.from(Cargo.class));
            Query q = entityManager.createQuery(cargoQuery);
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
