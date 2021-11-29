package watchdog.server.core.service;

import watchdog.server.core.database.base.TransactionManager;
import watchdog.server.core.database.dao.DaoUser;
import watchdog.server.core.database.dao.implementation.DaoFactory;
import watchdog.server.core.model.User;
import watchdog.server.core.utils.JsonUtils;
import watchdog.server.core.utils.SecurityUtils;

/**
 * Created by admin on 25.09.2016.
 */
public class UserService {

    DaoUser daoUser = DaoFactory.getDaoUser();

    public void createUser(String jsonString) {
        try {
            User user = JsonUtils.parseUser(jsonString);
            TransactionManager.startTransaction();
            user.setPassword(SecurityUtils.hashPassword(user.getPassword()));
            daoUser.persist(user);
            TransactionManager.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollbackTransaction();
        }
    }

    public User findUser(String login, String password) {
        try {
            TransactionManager.startTransaction();
            User user = daoUser.findByLoginAndPassword(login, SecurityUtils.hashPassword(password));
            System.out.println("login:"+login+" password:"+SecurityUtils.hashPassword(password));
            daoUser.persist(user);
            TransactionManager.endTransaction();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollbackTransaction();
            return null;
        }
    }

    public void createUser(User user) {
        try {
            TransactionManager.startTransaction();
            user.setPassword(SecurityUtils.hashPassword(user.getPassword()));
            daoUser.persist(user);
            TransactionManager.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollbackTransaction();
        }
    }

}
