package io.xico26.spotifum_neo.music;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/musics")
public class MusicController {

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id) {
        return "Hello, world!";
    }
}
