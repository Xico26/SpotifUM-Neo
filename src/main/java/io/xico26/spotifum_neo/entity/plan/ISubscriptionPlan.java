package io.xico26.spotifum_neo.entity.plan;

import io.xico26.spotifum_neo.entity.User;
import io.xico26.spotifum_neo.entity.music.Music;

/**
 * Interface implementada por todos os planos. Contém todos os métodos que indicam as permissões dos utilizadores.
 */
public interface ISubscriptionPlan {

    boolean canCreatePlaylist();

    boolean canSavePlaylist();

    boolean canGenerateFavouritesList();

    boolean canSaveAlbum();

    boolean podeAvancarRetroceder();

    boolean canCreateGenreList();

    boolean canListenCustomPlaylist();

    boolean canListenSingleMusic();

    void addPoints(Music music, User user);
}