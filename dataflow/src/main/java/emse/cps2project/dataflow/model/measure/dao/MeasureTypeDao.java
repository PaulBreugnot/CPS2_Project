package emse.cps2project.dataflow.model.measure.dao;

import emse.cps2project.dataflow.model.measure.MeasureType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureTypeDao extends JpaRepository<MeasureType, Integer> {
    MeasureType findByType(String type);
}
