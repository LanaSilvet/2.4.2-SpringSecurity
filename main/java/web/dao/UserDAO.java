package web.dao;

import web.model.Role;
import web.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface UserDAO {

    void addUser(User user);

    List<User> getAllUsers();

    void deleteUser(int userId);

    ArrayList<Role> findRolesByName(Role role);

    void updateUser(int id, User user);

    User getUser(int id);

    User getUserByName(String name);

    public Role addRole(Role role);
}
