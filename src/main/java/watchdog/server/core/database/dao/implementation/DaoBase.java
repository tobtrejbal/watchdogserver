package watchdog.server.core.database.dao.implementation;

import watchdog.server.core.database.base.DbsManager;
import watchdog.server.core.database.dao.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by admin on 25.09.2016.
 */
public class DaoBase<E,K> implements Dao<E,K> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<E> entityClass;

    public DaoBase() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        //entityManager = DbsManager.getEM();
    }

    public void createOrUpdate(E entity) {
        entityManager = DbsManager.getEM();
        try {
            entityManager.persist(entity);
        } catch (ConstraintViolationException ex) {
            entityManager.merge(entity);
        }
    }

    public void persist(E entity) {
        entityManager = DbsManager.getEM();
        entityManager.persist(entity);
    }


    public void remove(E entity) {
        entityManager = DbsManager.getEM();
        entityManager.remove(entity);
    }

    public E findById(K id) {
        entityManager = DbsManager.getEM();
        E entity = entityManager.find(entityClass, id);
        return entity;
    }

    public List<E> list() {
        entityManager = DbsManager.getEM();
        Query query = entityManager.createQuery("from " + entityClass.getName());
        List<E> result = query.getResultList();
        return result;
    }

    public void persistMultiple(List<E> es) {
        entityManager = DbsManager.getEM();
        for (int i = 0; i < es.size(); i++) {
            E e = es.get(i);
            entityManager.persist(e);
            if(i % 50== 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

}