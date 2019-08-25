package hello.service;

import hello.model.Role;
import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service // содержат бизнес-логику и вызывают методы на уровне хранилища.
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
//        user.setRoles(getRoleSet(user));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
//        user.setRoles(getRoleSet(user));
        userRepository.save(user);
    }

    @Override
    public void removeUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserLogin(String login) {
        return userRepository.findByLogin(login).get();
    }

//    @Override
//    public User findByUserLogin(String login) {
//        return userRepository.findByLogin(login);
//    }


//    private Set<Role> getRoleSet(User user) {
//        Role roleAdmin = roleService.getById(1);
//        Role roleUser = roleService.getById(2);
//        Set<Role> roleSet = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            System.out.println("ROLSE ROIS: " + role);
//            if (role.getRole().equals("ROLE_ADMIN")) {
//                roleSet.add(roleAdmin);
//            }
//            if (role.getRole().equals("ROLE_USER")) {
//                roleSet.add(roleUser);
//            }
//        }
//        return roleSet;
//    }
}
