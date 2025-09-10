package io.xico26.spotifum_neo;

import io.xico26.spotifum_neo.album.AlbumService;
import io.xico26.spotifum_neo.music.MusicService;
import io.xico26.spotifum_neo.playlist.PlaylistService;
import io.xico26.spotifum_neo.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserService userService;
    private final PlaylistService playlistService;
    private final AlbumService albumService;
    private final MusicService musicService;

    public MainController(UserService userService, PlaylistService playlistService, AlbumService albumService, MusicService musicService) {
        this.userService = userService;
        this.playlistService = playlistService;
        this.albumService = albumService;
        this.musicService = musicService;
    }

    @RequestMapping("/")
    public String index (Model model) {
        model.addAttribute("date", LocalDateTime.now());
        model.addAttribute("user", userService.findByUsername("xico"));
        model.addAttribute("playlists", playlistService.findAll());
        model.addAttribute("albums", albumService.findAll());
        model.addAttribute("recentMusics", musicService.findAll());

        return "home";
    }

}
