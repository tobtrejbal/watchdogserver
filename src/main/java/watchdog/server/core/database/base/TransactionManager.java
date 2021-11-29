package watchdog.server.core.database.base;

/**
 * Created by admin on 25.09.2016.
 */
public class TransactionManager {

    public static void startTransaction() throws Exception {
        switch (getType()) {
            case 0:
                DbsManager.openSession();
                //System.out.println("starting transaction");
                break;

            default:

                break;
        }
    }

    public static void endTransaction() throws Exception {
        switch (getType()) {
            case 0:
                DbsManager.commitSession();
                //System.out.println("ending transaction");
                break;

            default:

                break;
        }
    }

    public static void rollbackTransaction() {
        switch (getType()) {
            case 0:
                DbsManager.rollback();
                //System.out.println("rolling back transaction");
                break;

            default:

                break;
        }
    }

    public static void flushAndClear() {
        switch (getType()) {
            case 0:
                //DbsManager.flushAndClear();
                break;

            default:

                break;
        }
    }

    public static int getType() {
        return 0;
    }

}
