package io.xico26.spotifum_neo.listeningrecord;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ListeningRecordDAOImpl implements ListeningRecordDAO {
    private final EntityManager em;

    @Autowired
    public ListeningRecordDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ListeningRecord> findByUser(User u) {
        TypedQuery<ListeningRecord> query = em.createQuery("SELECT l FROM ListeningRecord l WHERE l.user = :user", ListeningRecord.class);
        query.setParameter("user", u);
        return query.getResultList();
    }

    @Override
    public void deleteByUser(User u) {
        em.createQuery("DELETE FROM ListeningRecord l WHERE l.user = :user")
                .setParameter("user", u)
                .executeUpdate();
    }

    @Override
    public void save (ListeningRecord listeningRecord) {
        em.persist(listeningRecord);
    }

    @Override
    public boolean hasListened (User u, int musicId) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM ListeningRecord l WHERE l.user = :user AND l.music.id = :music_id", Long.class);
        query.setParameter("user", u);
        query.setParameter("music_id", musicId);

        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public int getNumListened (User u) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM ListeningRecord l WHERE l.user = :user", Long.class);
        query.setParameter("user", u);

        return query.getSingleResult().intValue();
    }

    @Override
    public List<Music> getUniqueListens (User u) {
        TypedQuery<Music> query = em.createQuery("SELECT DISTINCT l.music FROM ListeningRecord l WHERE l.user = :user", Music.class);
        query.setParameter("user", u);
        return query.getResultList();
    }

    @Override
    public int getNumListensToMusic(User u, Music m) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM ListeningRecord l WHERE l.user = :user AND l.music = :music", Long.class);
        query.setParameter("user", u);
        query.setParameter("music", m);

        return query.getSingleResult().intValue();
    }
}
