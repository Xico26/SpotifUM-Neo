package io.xico26.spotifum_neo.playlist;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistDAOImpl implements PlaylistDAO {
    private final EntityManager em;

    @Autowired
    public PlaylistDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Playlist findById(int id) {
        try {
            return em.find(Playlist.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Playlist> findByUser(User user) {
        TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE p.creator = :user", Playlist.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Playlist> findPublicPlaylists() {
        return em.createQuery("SELECT p FROM Playlist p WHERE p.isPublic = true", Playlist.class).getResultList();
    }

    @Override
    public List<Playlist> findByTitle(String title) {
        TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE LOWER(p.name) LIKE :title", Playlist.class);
        query.setParameter("title", "%" + title.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Playlist> findAll() {
        return em.createQuery("FROM Playlist", Playlist.class).getResultList();
    }

    @Override
    public void save(Playlist p) {
        em.persist(p);
    }

    @Override
    public void deleteById(int id) {
        Playlist playlist = em.find(Playlist.class, id);
        if (playlist != null) {
            em.remove(playlist);
        }
    }

    @Override
    public void update(Playlist p) {
        em.merge(p);
    }

    @Override
    public List<Playlist> findAllWithMusic(Music music) {
        TypedQuery<Playlist> query = em.createQuery(
                    "SELECT p FROM Playlist p JOIN p.musics m WHERE m = :music", Playlist.class);
        query.setParameter("music", music);
        return query.getResultList();
    }
}
