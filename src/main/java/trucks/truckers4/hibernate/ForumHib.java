package trucks.truckers4.hibernate;

import trucks.truckers4.Model.Comment;
import trucks.truckers4.Model.Forum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ForumHib {

    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public ForumHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createForum(Forum forum) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(forum);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateForum(Forum forum) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(forum);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteForum(Forum forum) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Forum.class, forum.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Forum> getAllForums() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Forum> forumQuery = cb.createQuery(Forum.class);
            forumQuery.select(forumQuery.from(Forum.class));
            Query q = entityManager.createQuery(forumQuery);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return new ArrayList<>();
    }

    public Forum getForum(Forum forum) {
        entityManager = entityManagerFactory.createEntityManager();
        Forum newForum = entityManager.getReference(Forum.class, forum.getId());

        if (entityManager != null) entityManager.close();

        return newForum;
    }
}