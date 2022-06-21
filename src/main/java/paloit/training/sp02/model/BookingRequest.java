package paloit.training.sp02.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import paloit.training.sp02.dbmodel.Room;

import java.time.LocalDateTime;

public class BookingRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime starttime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endtime;
    private Long room_id;
    private Integer nbpeople;
    private Long user_id;

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Integer getNbpeople() {
        return nbpeople;
    }

    public void setNbpeople(Integer nbpeople) {
        this.nbpeople = nbpeople;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
