package hello.dao;

import hello.model.Role;
import hello.model.User;
import hello.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository // Это указывает на то, что класс определяет хранилище данных.
@Transactional // откат всех записей к предыдущему значению, если любая из операций в этом методе завершится неудачей
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    private RoleService roleService;

    @Autowired
    public UserDaoImpl(RoleService roleService){
        this.roleService = roleService;
    }

    @PersistenceContext
    public  void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        user.setRoles(getRoleSet(user));
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        user.setRoles(getRoleSet(user));
        entityManager.merge(user);
    }

    @Override
    public void removeUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUserById(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User findByUserLogin(String login) {
        String select = "from User where login = :login";
        Query query = entityManager.createQuery(select, User.class);
        query.setParameter("login", login);
        return (User) query.getResultList().get(0);
    }


    public boolean checkTitle(String login) {
        String select = "from User where login = :login";
        Query query = entityManager.createQuery(select);
        query.setParameter("login", login);
        return query.getResultList().isEmpty();
    }

    private Set<Role> getRoleSet(User user) {
        Role roleAdmin = roleService.getById(1);
        Role roleUser = roleService.getById(2);
        Set<Role> roleSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("1")) {
                roleSet.add(roleAdmin);
            }
            if (role.getRole().equals("2")) {
                roleSet.add(roleUser);
            }
        }
        return roleSet;
    }
}
