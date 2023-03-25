package trucks.truckers4.hibernate;

import trucks.truckers4.Model.Driver;
import trucks.truckers4.Model.Manager;
import trucks.truckers4.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createUser(User user) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void updateUser(User user) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void deleteUser(User user) {
        entityManager = entityManagerFactory.createEntityManager();
        if (user.getClass() == Manager.class) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(entityManager.find(Manager.class, user.getId()));
                entityManager.getTransaction().commit();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(entityManager.find(Driver.class, user.getId()));
                entityManager.getTransaction().commit();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public User getUserByLogin(String username, String password) {
        entityManager = entityManagerFactory.createEntityManager();
        Query q = null;
        CriteriaQuery<Driver> driverQuery = null;
        CriteriaQuery<Manager> managerQuery = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        driverQuery = cb.createQuery(Driver.class);
        Root<Driver> driverRoot = driverQuery.from(Driver.class);
        driverQuery.select(driverRoot).where(cb.and(cb.like(driverRoot.get("username"), username), cb.like(driverRoot.get("password"), password)));

        managerQuery = cb.createQuery(Manager.class);
        Root<Manager> managerRoot = managerQuery.from(Manager.class);
        managerQuery.select(managerRoot).where(cb.and(cb.like(managerRoot.get("username"), username), cb.like(managerRoot.get("password"), password)));


        try {
            q = entityManager.createQuery(driverQuery);
            return (User) q.getSingleResult();
        } catch (Exception e) {
        }

        try {
            q = entityManager.createQuery(managerQuery);
            return (User) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public Manager getManagerById(int id) {
        entityManager = entityManagerFactory.createEntityManager();
        Query q = null;
        CriteriaQuery<Manager> managerQuery = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        managerQuery = cb.createQuery(Manager.class);
        Root<Manager> managerRoot = managerQuery.from(Manager.class);
        managerQuery.select(managerRoot).where(cb.like(managerRoot.get("id"), String.valueOf(id)));


        try {
            q = entityManager.createQuery(managerQuery);
            return (Manager) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }

        return null;
    }

    public Driver getDriverById(int id) {
        entityManager = entityManagerFactory.createEntityManager();
        Query q = null;
        CriteriaQuery<Driver> driverQuery = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        driverQuery = cb.createQuery(Driver.class);
        Root<Driver> driverRoot = driverQuery.from(Driver.class);
        driverQuery.select(driverRoot).where(cb.like(driverRoot.get("id"), String.valueOf(id)));


        try {
            q = entityManager.createQuery(driverQuery);
            return (Driver) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
        return null;
    }

    public List<Driver> getAllDrivers() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Driver> driverQuery = cb.createQuery(Driver.class);
            driverQuery.select(driverQuery.from(Driver.class));
            Query q = entityManager.createQuery(driverQuery);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }

    public List<Manager> getAllManagers() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Manager> managerQuery = cb.createQuery(Manager.class);
            managerQuery.select(managerQuery.from(Manager.class));
            Query q = entityManager.createQuery(managerQuery);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }
}