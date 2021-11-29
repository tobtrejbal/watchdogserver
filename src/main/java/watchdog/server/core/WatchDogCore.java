package watchdog.server.core;

import watchdog.server.core.communication.CommManager;
import watchdog.server.core.communication.CommunicationListener;
import watchdog.server.core.communication.constants.NetworkCommunication;
import watchdog.server.core.communication.impl.Mqtt;
import watchdog.server.core.database.base.TransactionManager;
import watchdog.server.core.database.dao.DaoUser;
import watchdog.server.core.database.dao.implementation.DaoFactory;
import watchdog.server.core.database.dao.implementation.DaoUserImpl;
import watchdog.server.core.model.User;
import watchdog.server.core.service.DataService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by tobou on 15.10.2016.
 */
public class WatchDogCore {

    String serverAddress = "tcp://127.0.0.1:1883";
    //String serverAddress = "tcp://imitgw.uhk.cz:59708";
    String clientId = "watchdog_server";
    String[] topics = {NetworkCommunication.MQTT_SEND_DATA_TOPIC};
    int[] quos = {0};

    private static WatchDogCore sInstance;

    public static WatchDogCore getInstance() {
        if(sInstance == null) {
            sInstance = new WatchDogCore();
        }
        return sInstance;
    }

    CommManager commManager;
    DataService dataService;

    boolean online = false;

    CommunicationListener communicationListener = new CommunicationListener() {

        public void connectionSuccess() {
            System.out.println("connected");
            online = true;
        }

        public void connectionFail() {
            System.out.println("fail connected");
            online = false;
            connect();
        }

        public void connectionLost() {
            System.out.println("lost connected");
            online = false;
            connect();
        }

        public void messageArrived(String s, byte[] content) {
            System.out.println("message"+s);
            if(s.equals(NetworkCommunication.MQTT_SEND_DATA_TOPIC)) {
                dataService.saveDataBytes(content);
            }
        }

        public void messageSuccess() {

        }

        public void messageFail(String type, byte[] message) {

        }
    };

    private WatchDogCore() {
        initializeClasses();
        start();
        initDBS();
    }

    public void initializeClasses() {
        try {
            this.commManager = new Mqtt(communicationListener, serverAddress, clientId, topics, quos);
            this.dataService = new DataService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        connect();
    }

    public void connect() {
        commManager.connect("server","server");
    }

    public void disconnect() {
        commManager.disconnect();
    }

    public void createUser() {
        try {
            TransactionManager.startTransaction();
            DaoUser daoUser = DaoFactory.getDaoUser();
            User user = new User();
            user.setLogin("tobous");
            user.setPassword("password");
            daoUser.persist(user);
            TransactionManager.endTransaction();
        } catch (Exception e) {
            TransactionManager.rollbackTransaction();
            e.printStackTrace();
        }
    }

    public void initDBS() {
        System.out.println("creating dbs");
        DaoUser daoUser = DaoFactory.getDaoUser();
        try {
            TransactionManager.startTransaction();
            User userToDelete = daoUser.findByLogin("tobous");
            daoUser.clearSamplesForUser(userToDelete);
            daoUser.remove(userToDelete);
            TransactionManager.endTransaction();
        } catch (Exception e) {
            TransactionManager.rollbackTransaction();
            e.printStackTrace();
        }
        try {
            System.out.println("creating");
            TransactionManager.startTransaction();
            User user = new User();
            user.setLogin("tobous");
            user.setPassword("password");
            daoUser.persist(user);
            TransactionManager.endTransaction();
        } catch (Exception e) {
            TransactionManager.rollbackTransaction();
            e.printStackTrace();
        }
    }
}
