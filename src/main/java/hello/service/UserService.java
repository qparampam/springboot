package hello.service;


import hello.model.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);

    public void updateUser(User user);

    public void removeUser(int id);

    public User getUserById(int id);

    public List<User> listUsers();

    User findByUserLogin(String login);
//
//    boolean checkTitle(String login);
}
