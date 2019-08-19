package hello.dao;

import hello.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository // Это указывает на то, что класс определяет хранилище данных.
@Transactional // откат всех записей к предыдущему значению, если любая из операций в этом методе завершится неудачей
public class RoleDaoImpl implements RoleDao{

    private EntityManager entityManager;

    @PersistenceContext
    public  void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role getById(int id) {
        return entityManager.find(Role.class, id);
    }
}
