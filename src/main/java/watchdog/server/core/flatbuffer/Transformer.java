package watchdog.server.core.flatbuffer;

import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;
import java.nio.channels.InterruptibleChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import watchdog.server.core.flatbuffer.objects.*;
import watchdog.server.core.model.Sample;
import watchdog.server.core.model.SensorValue;
import watchdog.server.core.model.User;

import javax.jws.soap.SOAPBinding;

/**
 * Created by tobou on 15.10.2016.
 */

public class Transformer {

    public static List<Sample> fbDataToObjects(byte[] bytes) {
        List<Sample> samples = new ArrayList<Sample>();
        DataFB data = DataFB.getRootAsDataFB(ByteBuffer.wrap(bytes));
        for(int i = 0; i < data.samplesLength(); i++) {
            Sample sample = createSampleFromFB(data.samples(i), data.userLogin());
            samples.add(sample);
        }
        return samples;
    }

    public static Sample createSampleFromFB(SampleFB sampleFB, String login) {
        List<SensorValue> sampleValues = new ArrayList<SensorValue>();
        User user = new User();
        user.setLogin(login);
        Sample sample = new Sample(sampleFB.date(), sampleFB.batteryLevel(), sampleFB.lat(), sampleFB.lon());
        for(int i = 0; i < sampleFB.sensorValuesLength(); i++) {
            SensorValue sensorValue = createSensorValueFromFB(sampleFB.sensorValues(i));
            sampleValues.add(sensorValue);
            sensorValue.setSample(sample);
        }
        sample.setValues(sampleValues);
        sample.setUser(user);
        return sample;
    }

    public static SensorValue createSensorValueFromFB(SensorValueFB sensorValueFB) {
        float[] values = new float[sensorValueFB.sensorValuesLength()];
        for(int i = 0; i < values.length; i++) {
            values[i] = sensorValueFB.sensorValues(i);
        }
        SensorValue sensorValue = new SensorValue(sensorValueFB.sensorId(), values);
        return sensorValue;
    }

}
