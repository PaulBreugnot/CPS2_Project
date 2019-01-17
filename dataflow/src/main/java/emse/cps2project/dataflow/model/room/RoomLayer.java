package emse.cps2project.dataflow.model.room;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="room_layer")
public class RoomLayer {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "roomLayer")
    private List<Room> rooms;

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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
