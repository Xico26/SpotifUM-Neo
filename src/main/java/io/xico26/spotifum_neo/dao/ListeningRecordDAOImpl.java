package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.ListeningRecord;
import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class ListeningRecordDAOImpl implements ListeningRecordDAO {
    private EntityManagerFactory emf;

    public ListeningRecordDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<ListeningRecord> findByUser(User u) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ListeningRecord> query = em.createQuery("SELECT l FROM ListeningRecord l WHERE l.user == :user", ListeningRecord.class);
            query.setParameter("user", u);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteByUser(User u) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM ListeningRecord l WHERE l.user == :user")
                    .setParameter("user", u)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void save (ListeningRecord listeningRecord) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(listeningRecord);
        tx.commit();
        em.close();
    }

    @Override
    public boolean hasListened (User u, int musicId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM ListeningRecord l WHERE l.user == :user AND l.music.id = :music_id", Long.class);
            query.setParameter("user", u);
            query.setParameter("music_id", musicId);

            Long count = query.getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public int getNumListened (User u) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM ListeningRecord l WHERE l.user == :user", Long.class);
            query.setParameter("user", u);

            return query.getSingleResult().intValue();
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Music> getUniqueListens (User u) {
        EntityManager em = emf.createEntityManager();
        Set<Music> musics = new HashSet<Music>();
        try {
            TypedQuery<ListeningRecord> query = em.createQuery("SELECT l FROM ListeningRecord l WHERE l.user == :user", ListeningRecord.class);
            query.setParameter("user", u);

            List<ListeningRecord> recs = query.getResultList();

            for (ListeningRecord listeningRecord : recs) {
                musics.add(listeningRecord.getMusic());
            }

            return musics.stream().toList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public int getNumListensToMusic(User u, Music m) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM ListeningRecord l WHERE l.user == :user AND l.music = :music", Long.class);
            query.setParameter("user", u);
            query.setParameter("music", m);

            return query.getSingleResult().intValue();
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }
}
