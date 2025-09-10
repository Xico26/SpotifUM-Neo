package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.Library;
import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.entity.playlist.Playlist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class LibraryDAOImpl implements LibraryDAO {
    private final EntityManager em;

    @Autowired
    public LibraryDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Library findByUser(User u) {
        try {
            TypedQuery<Library> query = em.createQuery("FROM Library WHERE user = :user", Library.class);
            query.setParameter("user", u);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Library library) {
        em.persist(library);
    }

    @Override
    public void update(Library library) {
        em.merge(library);
    }

    @Override
    public List<Library> findAllWithMusic(Music music) {
        try {
            TypedQuery<Library> query = em.createQuery(
                    "SELECT l FROM Library l JOIN l.savedMusics m WHERE m = :music", Library.class);
            query.setParameter("music", music);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Library> findAllWithPlaylist(Playlist playlist) {
        try {
            TypedQuery<Library> query = em.createQuery(
                    "SELECT l FROM Library l JOIN l.savedPlaylists p WHERE p = :playlist", Library.class);
            query.setParameter("playlist", playlist);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
