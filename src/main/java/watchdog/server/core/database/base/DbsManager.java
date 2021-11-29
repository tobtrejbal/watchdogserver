package watchdog.server.core.database.base;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 25.09.2016.
 */
public class DbsManager {

    public static ReentrantLock databaseLock = new ReentrantLock();

    static final String persistanceUnitName= "watch.dog";

    private static EntityManager entityManager;

    private static final EntityManagerFactory factory;

    static {
        try {
            System.out.println("vytvarim session");
            factory = Persistence.createEntityManagerFactory(persistanceUnitName);
        }  catch (Throwable ex) {
            System.out
                    .println("Chyba (hibernate) - nepodařilo se vytvořit SessionFactory. "
                            + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static EntityManager getEM(){
        return entityManager;
    }

    protected static void openSession() throws Exception {
        // need to call before you interact with database
        if (databaseLock.tryLock(5000, TimeUnit.MILLISECONDS)) {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            //System.out.println("opened");
        }
    }

    protected static void commitSession() throws Exception {
        // need to call after you interact with database
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManager = null;
        databaseLock.unlock();
        //System.out.println("closed");
    }

    protected static void rollback() {
        // call in catch - in case something fails :P
        entityManager.getTransaction().rollback();
        entityManager.close();
        databaseLock.unlock();
        //System.out.println("rolledback");
    }

    protected static void flushAndClear() {
        // need to call after you interact with database
        entityManager.flush();
        entityManager.clear();
    }
}
