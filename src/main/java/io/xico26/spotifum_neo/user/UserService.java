package io.xico26.spotifum_neo.user;

import io.xico26.spotifum_neo.plan.ISubscriptionPlan;
import io.xico26.spotifum_neo.plan.SubscriptionPlanFactory;
import io.xico26.spotifum_neo.exceptions.InvalidLoginException;
import io.xico26.spotifum_neo.exceptions.InvalidParamsException;
import io.xico26.spotifum_neo.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findById(int id) {
        return userDAO.findById(id);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User login (String username, String password) {
        User user = findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidLoginException("Wrong Username or Password!");
        }
        return user;
    }

    @Transactional
    public void save(User user) {
        userDAO.save(user);
    }

    public void createUser(String username, String password, String name, String address, String email, LocalDate birthDate) {
        User newUser = new User(username, password, name, address, email, birthDate, "FREE");

        if (findByUsername(username) != null) {
            throw new InvalidParamsException("Username already used!");
        }

        if (findByEmail(email) != null) {
            throw new InvalidParamsException("Email already used!");
        }

        save(newUser);
    }

    public void setPlan (User u, String newPlan) {
        u.setSubscriptionPlan(newPlan);
        if (newPlan.equals("PREMIUM")) {
            u.addPoints(100);
        }
    }

    @Transactional
    public void removeUser (User u) {
        if (findById(u.getId()) == null) {
            throw new UserNotFoundException("User not found!");
        }

        userDAO.deleteById(u.getId());
    }

    public ISubscriptionPlan getSubscriptionPlan (User u) {
        return SubscriptionPlanFactory.createPlan(u.getSubscriptionPlan());
    }

    public void setIsAdmin (User u) {
        u.setIsAdmin(true);
        userDAO.update(u);
    }
}
