package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.ListeningRecord;
import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;

import java.util.List;

public interface ListeningRecordDAO {
    List<ListeningRecord> findByUser(User u);

    void deleteByUser(User u);

    void save(ListeningRecord listeningRecord);

    boolean hasListened (User u, int musicId);

    int getNumListened (User u);

    List<Music> getUniqueListens (User u);

    int getNumListensToMusic (User u, Music m);
}
