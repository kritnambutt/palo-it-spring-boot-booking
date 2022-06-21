package paloit.training.sp02.dbmodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_generator")
    @SequenceGenerator(name="room_generator", sequenceName = "room_seq")
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "max_size")
    private Integer maxSize;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_dt", nullable = false)
    private Date createDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_dt", nullable = false)
    private Date updateDt;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public Room() {

    }

    public Room(String roomName, String roomType, Integer maxSize) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.maxSize = maxSize;
        this.updateDt = new Date();
        this.updateDt = new Date();
    }

    @PrePersist
    private void onUpdate() {
        this.updateDt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}


