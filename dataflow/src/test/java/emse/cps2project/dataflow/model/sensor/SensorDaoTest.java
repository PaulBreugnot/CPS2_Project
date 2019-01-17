package emse.cps2project.dataflow.model.sensor;

import emse.cps2project.dataflow.model.room.Room;
import emse.cps2project.dataflow.model.room.RoomLayer;
import emse.cps2project.dataflow.model.room.dao.RoomLayerDao;
import emse.cps2project.dataflow.model.sensor.dao.SensorDao;
import emse.cps2project.dataflow.model.sensor.dao.SensorLayerDao;
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
public class SensorDaoTest {

    @Autowired
    private SensorDao sensorDao;

    @Autowired
    private SensorLayerDao sensorLayerDao;

    @Test
    public void SensorTableTest() {
        List<Sensor> sensors = sensorDao.findAll();
        for (Sensor sensor : sensors) {
            System.out.println("Sensor " + sensor.getId() + " geometry : " + sensor.getGeometry());
            System.out.println("Layer : " + sensor.getSensorLayer().getName());
        }
        assertThat(sensors).hasAtLeastOneElementOfType(Sensor.class);
    }

    @Test
    public void ItmSensorLayerTest() {
        List<SensorLayer> itmLayer = sensorLayerDao.findByName("sensoritm");
        assertThat(itmLayer)
                .hasAtLeastOneElementOfType(SensorLayer.class)
                .hasSize(1);
        List<Sensor> itmSensors = itmLayer.get(0).getSensors();
        for (Sensor sensor : itmSensors) {
            System.out.println("Sensor " + sensor.getId() + " geometry : " + sensor.getGeometry());
        }
        assertThat(itmSensors).hasAtLeastOneElementOfType(Sensor.class);
    }
}
