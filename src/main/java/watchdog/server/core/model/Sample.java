package watchdog.server.core.model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 23.09.2016.
 */

@Entity
@Table(name="sample")
public class Sample {

    @Id
    @GeneratedValue(generator = "sample_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sample_id_seq", sequenceName = "sample_id_seq", allocationSize = 50)
    @Column(name="sample_id")
    private long sampleId;

    @Column(name = "date_taken", nullable = false)
    private long date;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, foreignKey = @ForeignKey(name="FK_sample__user"))
    private User user;

    @Column(name = "battery_level", nullable = false)
    private int batteryLevel;

    @Column(name = "lat", nullable = false)
    private double lat;

    @Column(name = "lon", nullable = false)
    private double lon;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.DETACH, orphanRemoval = true, mappedBy = "sample")
    private List<SensorValue> values;

    public Sample() {

    }
    /**
     *
     * @param date
     * @param batteryLevel
     * @param lat
     * @param lon
     */
    public Sample(long date, int batteryLevel, double lat, double lon) {
        this.date = date;
        this.batteryLevel = batteryLevel;
        this.lat = lat;
        this.lon = lon;
    }

    public Sample(long dateTaken) {
        this.date = dateTaken;
    }

    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public List<SensorValue> getValues() {
        return values;
    }

    public void setValues(List<SensorValue> values) {
        this.values = values;
    }
}
