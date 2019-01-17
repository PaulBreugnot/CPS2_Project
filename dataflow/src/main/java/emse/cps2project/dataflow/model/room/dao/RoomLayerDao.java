package emse.cps2project.dataflow.model.room.dao;

import emse.cps2project.dataflow.model.room.RoomLayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomLayerDao extends JpaRepository<RoomLayer, Integer> {
    List<RoomLayer> findByName(String roomLayerName);
}
