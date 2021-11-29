package watchdog.server.core.database.dao.implementation;

import watchdog.server.core.database.dao.DaoSample;
import watchdog.server.core.database.dao.DaoUser;

/**
 * Created by admin on 25.09.2016.
 */
public class DaoFactory {

    private static DaoSample daoSample = new DaoSampleImpl();
    private static DaoUser daoUser = new DaoUserImpl();

    public static DaoSample getDaoSample() {
        return daoSample;
    }

    public static DaoUser getDaoUser() {
        return daoUser;
    }

}
