package watchdog.server.core.application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 06.10.2016.
 */
public class Core {

    private static Core sInstance;

    public static Core getsInstance() {
        if(sInstance == null) {

            sInstance = new Core();
        }
        return sInstance;
    }

    private Core() {

        users = new HashMap<String, String>();

    }

    private Map<String, String> users;

    public Map<String, String> getUsers() {
        return users;
    }

    public String getUser(String token) throws Exception {
        String userName = users.get(token);
        if(userName == null) {
            throw new Exception();
        } else {
            return userName;
        }
    }
}
