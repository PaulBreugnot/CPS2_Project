package emse.cps2project.dataflow.model.sensor.dao;

import emse.cps2project.dataflow.model.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDao extends JpaRepository<Sensor, Integer> {
}
