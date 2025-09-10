package io.xico26.spotifum_neo.album;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumDAOImpl implements AlbumDAO {
    private final EntityManager em;

    @Autowired
    public AlbumDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Album findById(int id) {
        return em.find(Album.class, id);
    }

    @Override
    public List<Album> findAll() {
        return em.createQuery("FROM Album", Album.class).getResultList();
    }

    @Override
    public void save(Album a) {
        em.persist(a);
    }

    @Override
    public void deleteById(int id) {
        Album a = em.find(Album.class, id);
        em.remove(a);
    }

    @Override
    public void update(Album a) {
        em.merge(a);
    }

    @Override
    public List<Album> findByTitle(String title) {
        TypedQuery<Album> query = em.createQuery("SELECT a FROM Album a LEFT JOIN FETCH a.musics LEFT JOIN FETCH a.artist WHERE LOWER(a.name) LIKE :title", Album.class);
        query.setParameter("title", "%" + title.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Album> findByArtist(String artist) {
        TypedQuery<Album> query = em.createQuery("SELECT a FROM Album a WHERE LOWER(a.artist.name) LIKE :artist", Album.class);
        query.setParameter("artist", "%" + artist.toLowerCase() + "%");
        return query.getResultList();
    }
}
