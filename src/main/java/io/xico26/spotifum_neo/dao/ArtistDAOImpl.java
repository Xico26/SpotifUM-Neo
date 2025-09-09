package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.Album;
import io.xico26.spotifum_neo.entity.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ArtistDAOImpl implements ArtistDAO {
    private final EntityManagerFactory emf;

    public ArtistDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Artist findById(int id) {
        EntityManager em = emf.createEntityManager();
        Artist a = em.find(Artist.class, id);
        em.close();
        return a;
    }

    @Override
    public List<Artist> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Artist> artists = em.createQuery("FROM Artist", Artist.class).getResultList();
        em.close();
        return artists;
    }

    @Override
    public Artist findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Artist> query = em.createQuery("SELECT a FROM Artist a WHERE LOWER(a.name) = :name", Artist.class);
            query.setParameter("name", name.toLowerCase());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Artist a) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }  finally {
            em.close();
        }
    }

    @Override
    public void delete(Artist a) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Artist a) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
        }  catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void addAlbum (Artist artist, Album album) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Artist a = em.merge(artist);
            a.getAlbums().add(album);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
