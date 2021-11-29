package watchdog.server.core.communication;

/**
 * Created by tobou on 15.10.2016.
 */

public interface CommManager {

    void connect(String userName, String password);
    void disconnect();
    void sendMessage(String type, byte[] content);

}
