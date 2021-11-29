package watchdog.server.core.model;

import javax.persistence.*;

/**
 * Created by Tobous on 5. 11. 2014.
 *
 */

@Entity
@Table(name="sensorvalue")
public class SensorValue {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="sensor_value_id")
    private long sensorValueId;

    /**
     *
     */
    @Column(name = "type", nullable = false)
    private int type;

    /**
     *
     */
    @Column(name = "sensor_values", nullable = false)
    private String values;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id", nullable = false, foreignKey = @ForeignKey(name="FK_sensorvalue_sample"))
    private Sample sample;

    public SensorValue() {

    }

    /**
     *
     * @param type
     * @param values
     */
    public SensorValue(int type, float[] values) {
        this.type = type;
        String valuesS = "";
        for(float v : values) {
            valuesS = valuesS + ":" + v;
        }
        if(values.length > 0) {
            valuesS = valuesS.substring(1,valuesS.length());
        }
        this.values = valuesS;
    }

    /**
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public String getValues() {
        return values;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public long getSensorValueId() {
        return sensorValueId;
    }

    public void setSensorValueId(long sensorValueId) {
        this.sensorValueId = sensorValueId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
