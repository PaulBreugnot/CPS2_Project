package emse.cps2project.dataflow.model.sensor;

import emse.cps2project.dataflow.model.measure.MeasureType;
import emse.cps2project.dataflow.model.observation.Observation;
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

    @Test
    public void availableMeasuresTest() {
        Sensor sensor = sensorDao.findById(1).orElse(null);
        assertThat(sensor).isNotEqualTo(null);
        for (MeasureType measureType : sensor.getAvailableMeasureTypes()) {
            System.out.println("MeasureType available : " + measureType.getType() + " (" + measureType.getUnit() + ")");
        }
        assertThat(sensor.getAvailableMeasureTypes()).hasAtLeastOneElementOfType(MeasureType.class);
        assertThat(sensor.getAvailableMeasureTypes()).hasSize(2);

    }

    @Test
    public void observationTests() {
        Sensor sensor = sensorDao.findById(1).orElse(null);
        assertThat(sensor).isNotEqualTo(null);
        for (Observation obs : sensor.getObservations()) {
            System.out.println("Observation at " + obs.getTimestamp() +
                    " for " + obs.getMeasureType().getType() +
                    " : " + obs.getValue() +
                    " " + obs.getMeasureType().getUnit());
        }
        assertThat(sensor.getObservations()).hasAtLeastOneElementOfType(Observation.class);
    }
}
