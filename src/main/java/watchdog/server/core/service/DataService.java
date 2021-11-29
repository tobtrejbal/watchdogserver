package watchdog.server.core.service;

import watchdog.server.core.database.base.TransactionManager;
import watchdog.server.core.database.dao.DaoSample;
import watchdog.server.core.database.dao.DaoUser;
import watchdog.server.core.database.dao.implementation.DaoFactory;
import watchdog.server.core.model.Sample;
import watchdog.server.core.model.User;
import watchdog.server.core.flatbuffer.Transformer;
import watchdog.server.core.utils.JsonUtils;

import java.util.List;

/**
 * Created by admin on 25.09.2016.
 */
public class DataService {

    DaoSample daoSample = DaoFactory.getDaoSample();
    DaoUser daoUser = DaoFactory.getDaoUser();

    public boolean saveDataBytes(byte[] data) {
        try {
            long startTime = System.currentTimeMillis();
            List<Sample> samples = Transformer.fbDataToObjects(data);
            //System.out.println("pocet vzorku: " + samples.size());
            TransactionManager.startTransaction();
            User user = daoUser.findByLogin(samples.get(0).getUser().getLogin());
            for (int i = 0; i < samples.size(); i++) {
                Sample sample = samples.get(i);
                sample.setUser(user);
            }
            daoSample.persistMultiple(samples);
            TransactionManager.endTransaction();
            System.out.println("time to take:"+(System.currentTimeMillis() - startTime));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollbackTransaction();
            return false;
        }
    }

    /**public boolean saveDataJson(String userLogin, String data) {
     try {
     long startTime = System.currentTimeMillis();
     List<Sample> samples = JsonUtils.parseSamples(data);
     System.out.println("pocet vzorku: " + samples.size());
     TransactionManager.startTransaction();
     User user = daoUser.findByLogin(userLogin);
     for (int i = 0; i < samples.size(); i++) {
     Sample sample = samples.get(i);
     sample.setUser(user);
     }
     daoSample.persistMultiple(samples);
     TransactionManager.endTransaction();
     System.out.println("time to take:"+(System.currentTimeMillis() - startTime));
     return true;
     } catch (Exception e) {
     e.printStackTrace();
     TransactionManager.rollbackTransaction();
     return false;
     }
     }*/

}
