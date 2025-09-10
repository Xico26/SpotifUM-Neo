package io.xico26.spotifum_neo.plan;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;

import java.io.Serializable;

/**
 * Plano de subscrição Premium Top. Atribui 2,5% dos pontos acumulados por cada nova música ouvida.
 */
public class PremiumPlan implements ISubscriptionPlan, Serializable {
    private static double bonusPercentual = 0.025;

    public boolean canCreatePlaylist() {
        return true;
    }

    public boolean canSavePlaylist() {
        return true;
    }

    public boolean canGenerateFavouritesList() {
        return true;
    }

    public boolean canSaveAlbum() {
        return true;
    }

    public boolean podeAvancarRetroceder() {
        return true;
    }

    public boolean canCreateGenreList() {
        return true;
    }

    public boolean canListenCustomPlaylist() {
        return true;
    }

    public boolean canListenSingleMusic() {
        return true;
    }

    public void addPoints(Music music, User user) {
        // if (!user.ouviuMusica(music)) {
            int pontos = user.getPoints();
            int bonus = (int)(pontos * bonusPercentual);
            user.addPoints(bonus);
        // }
    }

    public static double getBonusPercentual() {
        return bonusPercentual;
    }

    public static void setBonusPercentual(double bonus) {
        bonusPercentual = bonus;
    }

    public String toString() {
        return "Premium Top";
    }
}
