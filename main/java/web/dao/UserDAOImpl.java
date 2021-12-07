package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void addUser(User user) {
        getSession().save(user);
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "select * from users";
        List<User> usersList = getSession().createSQLQuery(hql)
                .addEntity(User.class)
                .list();
        return usersList;
    }

    @Override
    public void deleteUser(int userId) {
        String hql = "DELETE FROM users_roles WHERE user_id = :userId";
        getSession().createSQLQuery(hql)
                .setParameter("userId", userId).executeUpdate();
        User user = getSession().get(User.class, userId);
        getSession().delete(user);
    }

    @Override
    public ArrayList<Role> findRolesByName(Role role) {
        String hql = "FROM Role WHERE role = :role";
        List list = getSession().createQuery(hql)
                .setParameter("role", role.getRole()).getResultList();
        return new ArrayList<Role>(list);
    }

    @Override
    public void updateUser(int id, User user) {
        getSession().update(user);
    }

    @Override
    public User getUser(int id) {
        return getSession().get(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        String hql = "FROM User WHERE name = :name";
        return getSession().createQuery(hql, User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Role addRole(Role role) {
        Integer save1 = (Integer)getSession().save(role);

        return new Role(save1.intValue(), role.getRole());
    }

}
