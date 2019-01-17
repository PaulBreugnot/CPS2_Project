package emse.cps2project.dataflow.model.room;

import javax.persistence.*;

@Entity
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="geom")
    private String geometry;

    @Column(name="refoffice")
    private String refoffice;


    @ManyToOne
    @JoinColumn(name="id_layer")
    private RoomLayer roomLayer;

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

    public RoomLayer getRoomLayer() {
        return roomLayer;
    }

    public void setRoomLayer(RoomLayer roomLayer) {
        this.roomLayer = roomLayer;
    }
}
