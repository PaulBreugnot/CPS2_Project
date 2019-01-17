package emse.cps2project.dataflow.model.room.dao;

import emse.cps2project.dataflow.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItmRoomDao extends JpaRepository<Room, Integer> {
}
