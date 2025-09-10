package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.entity.playlist.Playlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistDAOImpl implements PlaylistDAO {
    private final EntityManagerFactory emf;

    public PlaylistDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Playlist findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Playlist.class, id);
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Playlist> findByUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE p.creator = :user", Playlist.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Playlist> findPublicPlaylists() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE p.isPublic = true", Playlist.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Playlist> findByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE LOWER(p.name) LIKE :title", Playlist.class);
            query.setParameter("title", "%" + title.toLowerCase() + "%");
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Playlist> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Playlist> playlists = em.createQuery("FROM Playlist", Playlist.class).getResultList();
        em.close();
        return playlists;
    }

    @Override
    public void save(Playlist p) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }  finally {
            em.close();
        }
    }

    @Override
    public void delete(Playlist p) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Playlist p) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        }  catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Playlist> findAllWithMusic(Music music) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Playlist> query = em.createQuery(
                    "SELECT p FROM Playlist p JOIN p.musics m WHERE m = :music", Playlist.class);
            query.setParameter("music", music);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
