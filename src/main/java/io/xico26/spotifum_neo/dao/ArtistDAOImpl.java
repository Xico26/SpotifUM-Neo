package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.Album;
import io.xico26.spotifum_neo.entity.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistDAOImpl implements ArtistDAO {
    private final EntityManager em;

    @Autowired
    public ArtistDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Artist findById(int id) {
        return em.find(Artist.class, id);
    }

    @Override
    public List<Artist> findAll() {
        return em.createQuery("FROM Artist", Artist.class).getResultList();
    }

    @Override
    public Artist findByName(String name) {
        try {
            TypedQuery<Artist> query = em.createQuery("SELECT a FROM Artist a WHERE LOWER(a.name) = :name", Artist.class);
            query.setParameter("name", name.toLowerCase());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Artist a) {
        em.persist(a);
    }

    @Override
    public void deleteById(int id) {
        Artist a = em.find(Artist.class, id);
        em.remove(a);
    }

    @Override
    public void update(Artist a) {
        em.merge(a);
    }

    @Override
    public void addAlbum (Artist artist, Album album) {
        try {
            Artist a = em.merge(artist);
            a.getAlbums().add(album);
        } catch (Exception e) {
            throw e;
        }
    }
}
