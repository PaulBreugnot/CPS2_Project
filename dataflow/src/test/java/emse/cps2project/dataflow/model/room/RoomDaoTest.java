package emse.cps2project.dataflow.model.room;

import emse.cps2project.dataflow.model.room.dao.RoomDao;
import emse.cps2project.dataflow.model.room.dao.RoomLayerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RoomDaoTest {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomLayerDao roomLayerDao;

    @Test
    public void RoomTableTest() {
        List<Room> rooms = roomDao.findAll();
        for (Room room : rooms) {
            System.out.println("Room " + room.getId() + " geometry : " + room.getGeometry());
            System.out.println("Layer : " + room.getRoomLayer().getName());
        }
        assertThat(rooms).hasAtLeastOneElementOfType(Room.class);
    }

    @Test
    public void ItmRoomLayerTest() {
        List<RoomLayer> itmLayer = roomLayerDao.findByName("itm");
        assertThat(itmLayer)
                .hasAtLeastOneElementOfType(RoomLayer.class)
                .hasSize(1);
        List<Room> itmRooms = itmLayer.get(0).getRooms();
        for (Room room : itmRooms) {
            System.out.println("Room " + room.getId() + " geometry : " + room.getGeometry());
        }
        assertThat(itmRooms).hasAtLeastOneElementOfType(Room.class);
    }
}
