package trucks.truckers4.hibernate;

import trucks.truckers4.Model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CommentHib {

    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createComment(Comment comment) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void updateComment(Comment comment) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public void deleteComment(Comment comment) {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Comment.class, comment.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(entityManager!=null) entityManager.close();
        }
    }

    public Comment getComment(Comment comment) {
        entityManager = entityManagerFactory.createEntityManager();
        Comment newComment = entityManager.getReference(Comment.class, comment.getId());
        if (comment.getDriverAuthor() != null) {
            newComment.setDriverAuthor(comment.getDriverAuthor());
        } else {
            newComment.setManagerAuthor(comment.getManagerAuthor());
        }

        if (entityManager != null) entityManager.close();

        return newComment;
    }
}