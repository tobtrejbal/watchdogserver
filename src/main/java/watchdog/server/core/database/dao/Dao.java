package watchdog.server.core.database.dao;

import java.util.List;

/**
 * Created by admin on 25.09.2016.
 */
public interface Dao <E,K> {

    void createOrUpdate(E entity);
    void persist(E entity);
    void remove(E entity);
    E findById(K id);
    List<E> list();
    void persistMultiple(List<E> eList);

}
