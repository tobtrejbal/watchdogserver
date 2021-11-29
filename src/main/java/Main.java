import watchdog.server.core.WatchDogCore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by admin on 23.09.2016.
 */
public class Main implements ServletContextListener {

    public static void main(final String[] args) throws Exception {

    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Starting");
        WatchDogCore.getInstance();

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}