package emse.cps2project.dataflow.model.room;

import javax.persistence.*;

@Entity
@Table(name="itm")
public class ItmRoom {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="geom")
    private String geometry;

    @Column(name="refoffice")
    private String refoffice;

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

    public String getRefoffice() {
        return refoffice;
    }

    public void setRefoffice(String refoffice) {
        this.refoffice = refoffice;
    }
}
