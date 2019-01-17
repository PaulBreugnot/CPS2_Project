package emse.cps2project.dataflow.model.sensor;

import emse.cps2project.dataflow.model.measure.MeasureType;
import emse.cps2project.dataflow.model.observation.Observation;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sensor")
public class Sensor {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="geom")
    private String geometry;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="id_layer")
    private SensorLayer sensorLayer;

    @Column(name="topic")
    private String topic;

    @ManyToMany
    @JoinTable(
            name = "sensor_available_measures",
            joinColumns = { @JoinColumn(name = "id_sensor") },
            inverseJoinColumns = { @JoinColumn(name = "id_measure_type") }
    )
    private List<MeasureType> availableMeasureTypes;

    @OneToMany(mappedBy = "sensor", fetch=FetchType.LAZY)
    private List<Observation> observations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorLayer getSensorLayer() {
        return sensorLayer;
    }

    public void setSensorLayer(SensorLayer sensorLayer) {
        this.sensorLayer = sensorLayer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<MeasureType> getAvailableMeasureTypes() {
        return availableMeasureTypes;
    }

    public void setAvailableMeasureTypes(List<MeasureType> availableMeasureTypes) {
        this.availableMeasureTypes = availableMeasureTypes;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }
}
