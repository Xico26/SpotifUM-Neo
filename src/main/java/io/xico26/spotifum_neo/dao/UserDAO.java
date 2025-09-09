package io.xico26.spotifum_neo.dao;


import io.xico26.spotifum_neo.entity.User;

import java.util.List;

public interface UserDAO {
    User findByUsername(String username);

    User findById (int userId);

    User findByEmail (String email);

    List<User> findAll();

    void save(User user);

    void delete(User user);

    void update(User user);
}
