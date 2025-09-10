package io.xico26.spotifum_neo.user;

import java.util.List;

public interface UserDAO {
    User findByUsername(String username);

    User findById (int userId);

    User findByEmail (String email);

    List<User> findAll();

    void save(User user);

    void deleteById(int id);

    void update(User user);
}
