package io.xico26.spotifum_neo.plan;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.music.Music;

import java.io.Serializable;

/**
 * Plano de subscrição Premium Base. Atribui 10 pontos por música.
 */
public class PlusPlan implements ISubscriptionPlan, Serializable {
    private static int pontosPorMusica = 10;


    public boolean canCreatePlaylist() {
        return true;
    }

    public boolean canSavePlaylist() {
        return true;
    }

    public boolean canSaveAlbum() {
        return true;
    }

    public boolean podeAvancarRetroceder() {
        return true;
    }

    public boolean canGenerateFavouritesList() {
        return false;
    }

    public boolean canCreateGenreList() {
        return false;
    }

    public boolean canListenSingleMusic() {
        return true;
    }

    public void addPoints(Music music, User user) {
        user.addPoints(pontosPorMusica);
    }

    public boolean canListenCustomPlaylist() {
        return true;
    }

    public static int getPontosPorMusica() {
        return pontosPorMusica;
    }

    public static void setPontosPorMusica(int pontos) {
        pontosPorMusica = pontos;
    }

    public String toString() {
        return "Premium Base";
    }
}