package watchdog.server.core.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import watchdog.server.core.model.Sample;
import watchdog.server.core.model.User;

import java.util.List;

/**
 * Created by admin on 25.09.2016.
 */
public class JsonUtils {

    public static List<Sample> parseSamples(String json) {
        Gson gson = new Gson();
        List<Sample> samples = gson.fromJson(json, new TypeToken<List<Sample>>(){}.getType());
        return samples;
    }

    public static Sample parseSample(String json) {
        Gson gson = new Gson();
        Sample sample = gson.fromJson(json, Sample.class);
        return sample;
    }

    public static User parseUser(String json) {
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        return user;
    }

}
