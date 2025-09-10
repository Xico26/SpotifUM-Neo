package io.xico26.spotifum_neo.music;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicDAOImpl implements MusicDAO {
    private final EntityManager em;

    @Autowired
    public MusicDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Music findById(int id) {
        return em.find(Music.class, id);
    }

    @Override
    public List<Music> findAll() {
        return em.createQuery("SELECT m FROM Music m", Music.class).getResultList();
    }

    @Override
    public void save(Music music) {
        try {
            if (music.getId() == 0) {
                em.persist(music);
            } else {
                em.merge(music);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteById(int id) {
        Music music = em.find(Music.class, id);
        if  (music != null) {
            em.remove(music);
        }
    }

    @Override
    public int getMusicCount() {
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(m) FROM Music m", Long.class);
            return query.getSingleResult().intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public List<Music> findByTitle(String title) {
        TypedQuery<Music> query = em.createQuery("SELECT m FROM Music m WHERE LOWER(m.title) LIKE :title", Music.class);
        query.setParameter("title", "%" + title.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Music> getMusicsByGenre(String genre) {
        TypedQuery<Music> query = em.createQuery("SELECT m FROM Music m WHERE m.genre == :genre", Music.class);
        query.setParameter("genre", genre);
        return query.getResultList();
    }
}