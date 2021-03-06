package emse.cps2project.dataflow.model.measure;

import emse.cps2project.dataflow.model.observation.Observation;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="measure_type")
public class MeasureType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="type")
    private String type;

    @Column(name="unit")
    private String unit;

    @OneToMany(mappedBy = "measureType", fetch = FetchType.EAGER)
    private List<Observation> observations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }
}
