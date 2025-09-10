package io.xico26.spotifum_neo.service;

import io.xico26.spotifum_neo.dao.ListeningRecordDAO;
import io.xico26.spotifum_neo.entity.ListeningRecord;
import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;
import io.xico26.spotifum_neo.entity.plan.ISubscriptionPlan;
import io.xico26.spotifum_neo.entity.plan.SubscriptionPlanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListeningRecordService {
    private final ListeningRecordDAO lrDAO;
    private final UserService userService;

    @Autowired
    public ListeningRecordService(ListeningRecordDAO lrDAO, UserService userService) {
        this.lrDAO = lrDAO;
        this.userService = userService;
    }

    @Transactional
    public void clearHistory(User user) {
        lrDAO.deleteByUser(user);
    }

    @Transactional
    public void registerMusicPlay(User u, Music m) {
        ISubscriptionPlan plan = SubscriptionPlanFactory.createPlan(u.getSubscriptionPlan());
        plan.addPoints(m, u);

        ListeningRecord lr = new ListeningRecord(u, m, LocalDateTime.now());
        lrDAO.save(lr);

        userService.save(u);
    }

    public boolean hasListenedMusic (User u, Music m) {
        return lrDAO.hasListened(u, m.getId());
    }

    public int getNumListened (User u) {
        return  lrDAO.getNumListened(u);
    }

    public List<Music> getUniqueListens (User u) {
        return lrDAO.getUniqueListens(u);
    }

    public int getNumListensToMusic (User u, Music m) {
        return lrDAO.getNumListensToMusic(u, m);
    }

    public String playMusic (User u, Music m) {
        // create record
        ListeningRecord lr = new ListeningRecord(u, m, LocalDateTime.now());
        lrDAO.save(lr);

        // add points
        userService.getSubscriptionPlan(u).addPoints(m, u);

        // save user
        userService.save(u);

        return m.toString() + "\n\n" + m.getLyrics();
    }

}
