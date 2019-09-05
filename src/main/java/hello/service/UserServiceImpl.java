package hello.service;

import hello.model.Role;
import hello.model.User;
import hello.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);

        for (Role role : user.getRoles()){
            if(roleRepository.findByRole(role.getRole()) != null){
                user.setRoles(getRoleSet(user));
            }
        }

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

    private Set<Role> getRoleSet(User user) {
        Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
        Role roleUser = roleRepository.findByRole("ROLE_USER");
        Set<Role> roleSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ROLE_ADMIN")) {
                roleSet.add(roleAdmin);
            }
            if (role.getRole().equals("ROLE_USER")) {
                roleSet.add(roleUser);
            }
        }
        return roleSet;
    }
}
