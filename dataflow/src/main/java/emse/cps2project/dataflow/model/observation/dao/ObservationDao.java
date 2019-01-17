package emse.cps2project.dataflow.model.observation.dao;

import emse.cps2project.dataflow.model.observation.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationDao extends JpaRepository<Observation, Integer> {
}
