package emse.cps2project.dataflow.model.observation;

import emse.cps2project.dataflow.model.measure.MeasureType;
import emse.cps2project.dataflow.model.sensor.Sensor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="observation")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "value")
    private float value;

    @ManyToOne
    @JoinColumn(name="id_measure_type")
    private MeasureType measureType;

    @ManyToOne
    @JoinColumn(name="id_sensor")
    private Sensor sensor;

    public Observation() {

    }

    public Observation(Date timestamp, float value, MeasureType measureType, Sensor sensor) {
        this.timestamp = timestamp;
        this.value = value;
        this.measureType = measureType;
        this.sensor = sensor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
