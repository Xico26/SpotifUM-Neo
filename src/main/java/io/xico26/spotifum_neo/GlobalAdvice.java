package io.xico26.spotifum_neo;

import io.xico26.spotifum_neo.user.User;
import io.xico26.spotifum_neo.user.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAdvice {
    private final UserService userService;

    public GlobalAdvice(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User getUser() {
        return userService.findByUsername("xico");
    }
}
