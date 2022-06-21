package paloit.training.sp02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import paloit.training.sp02.dbmodel.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT r.* " +
            "FROM room r " +
            "WHERE r.max_size >= :nbpeople " +
            "       AND NOT EXISTS (SELECT b.* FROM booking b " +
            "                       WHERE b.room_id = :room_id AND (:starttime, :endtime) OVERLAPS (b.start_dt, b.end_dt))"
            , nativeQuery = true)
    List<Room> findAvailableRoomBooking(
            LocalDateTime starttime,
            LocalDateTime endtime,
            Integer nbpeople,
            Long room_id
    );

    @Query(value = "SELECT r.* " +
            "FROM room r " +
            "WHERE r.max_size >= :nbpeople " +
            "       AND NOT EXISTS (SELECT b.* FROM booking b " +
            "                       WHERE b.room_id = r.id AND (:starttime, :endtime) OVERLAPS (b.start_dt, b.end_dt))"
            , nativeQuery = true)
    List<Room> findAvailableRoomBooking(LocalDateTime starttime, LocalDateTime endtime, Integer nbpeople);
}
