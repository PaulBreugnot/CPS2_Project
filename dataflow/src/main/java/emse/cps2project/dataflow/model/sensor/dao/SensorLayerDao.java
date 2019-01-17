package emse.cps2project.dataflow.model.sensor.dao;

import emse.cps2project.dataflow.model.sensor.SensorLayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorLayerDao extends JpaRepository<SensorLayer, Integer> {
    List<SensorLayer> findByName(String sensorLayerName);
}
