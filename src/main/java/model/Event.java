package model;

import javax.persistence.*;


@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "event_name")
    private String eventName;


    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private File file;


    public Event() {
    }


    public Event(Long id, User user, String eventName, File file) {
        this.id = id;
        this.user = user;
        this.eventName = eventName;
        this.file = file;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user +
                ", eventName='" + eventName + '\'' +
                ", file=" + file +
                '}';
    }
}
