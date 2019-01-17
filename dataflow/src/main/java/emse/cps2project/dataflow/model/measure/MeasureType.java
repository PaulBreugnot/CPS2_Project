package emse.cps2project.dataflow.model.measure;

import javax.persistence.*;

@Entity
@Table(name="measure_type")
public class MeasureType {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="type")
    private String type;

    @Column(name="unit")
    private String unit;

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
}
