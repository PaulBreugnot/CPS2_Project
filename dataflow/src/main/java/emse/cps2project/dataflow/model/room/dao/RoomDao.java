package emse.cps2project.dataflow.model.room.dao;

import emse.cps2project.dataflow.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDao extends JpaRepository<Room, Integer> {
}
