package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;
import web.model.MyUserDetails;
import web.model.Role;
import web.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDao;

    @Autowired
    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {

        checkRoles(user);
        userDao.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void deleteUser(int userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public ArrayList<Role> findRolesByName(Role role) {
        return userDao.findRolesByName(role);
    }

    @Override
    public void updateUser(int id, User user) {

        checkRoles(user);
        userDao.updateUser(id, user);
    }

    private void checkRoles(User user) {
//       0 создаешь новый пустой сет
//       1 берешь роли
//       2 ищещь эту роль в базе , находишь и кладешь ее в новый сет, если не находишь, то сохраняешь ее в базе и потом кладешь в сет
//       3 новый сет кладешь в юзера тем самым заменяешь старые роли на новые уже с айдишниками из базы

        Set<Role> newRoles = new HashSet<>();

        for (Role role : user.getRoles()) {
            ArrayList<Role> rolesByName = userDao.findRolesByName(role);
            if (rolesByName.isEmpty()) {
                newRoles.add(role);
            } else {
                newRoles.add(rolesByName.get(0));
            }
        }
        user.setRoles(newRoles);
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public void addRole(Role role) {
        userDao.addRole(role);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.getUserByName(name);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", name));
        }

        Set<Role> roles = user.getRoles();

        return new MyUserDetails(user, roles);
    }
}
