package emse.cps2project.dataflow.model.sensor;

import javax.persistence.*;

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
}
