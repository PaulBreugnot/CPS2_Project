package emse.cps2project.dataflow.model.room;

import emse.cps2project.dataflow.model.room.dao.ItmRoomDao;
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
    private ItmRoomDao itmLayerDao;

    @Test
    public void itmTableTest() {
        List<ItmRoom> itmRooms = itmLayerDao.findAll();
        for (ItmRoom roomSet : itmRooms) {
            System.out.println("Room " + roomSet.getId() + " geometry : " + roomSet.getGeometry());
        }
        assertThat(itmRooms).hasAtLeastOneElementOfType(ItmRoom.class);
    }
}
