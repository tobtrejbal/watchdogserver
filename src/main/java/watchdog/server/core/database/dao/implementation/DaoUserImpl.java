package watchdog.server.core.database.dao.implementation;

import watchdog.server.core.database.base.DbsManager;
import watchdog.server.core.database.dao.DaoUser;
import watchdog.server.core.model.User;

import javax.persistence.Query;

/**
 * Created by admin on 25.09.2016.
 */
public class DaoUserImpl  extends DaoBase<User,Long> implements DaoUser {

    protected DaoUserImpl() {}


    public User findByLogin(String login) {
        entityManager = DbsManager.getEM();
        String sql = "Select u FROM  User as u WHERE u.login LIKE :login";
        Query query = entityManager.createQuery(sql);
        query.setParameter("login",login);
        return (User) query.getResultList().get(0);
    }

    public User findByLoginAndPassword(String login, String password) {
        entityManager = DbsManager.getEM();
        String sql = "Select u FROM  User as u WHERE u.login LIKE :login AND u.password LIKE :password";
        Query query = entityManager.createQuery(sql);
        query.setParameter("login",login);
        query.setParameter("password", password);
        return (User) query.getResultList().get(0);
    }

    public void clearSamplesForUser(User user) {
        entityManager = DbsManager.getEM();
        Query q = entityManager.createNativeQuery("DELETE FROM sample WHERE user_id = :id");
        q.setParameter("id", user.getUser_id());
        q.executeUpdate();
    }
}

