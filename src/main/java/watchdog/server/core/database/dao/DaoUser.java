package watchdog.server.core.database.dao;

import watchdog.server.core.model.User;

/**
 * Created by admin on 25.09.2016.
 */
public interface DaoUser extends Dao<User, Long> {

    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);
    void clearSamplesForUser(User user);

}
