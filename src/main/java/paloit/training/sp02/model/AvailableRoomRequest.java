package paloit.training.sp02.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AvailableRoomRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime starttime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endtime;
    private Integer nbpeople;

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

    public Integer getNbpeople() {
        return nbpeople;
    }

    public void setNbpeople(Integer nbpeople) {
        this.nbpeople = nbpeople;
    }
}
