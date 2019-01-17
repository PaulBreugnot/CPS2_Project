package emse.cps2project.dataflow.model.sensor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sensor_layer")
public class SensorLayer {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "sensorLayer")
    private List<Sensor> sensors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
