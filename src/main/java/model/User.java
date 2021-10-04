package model;

import javax.persistence.*;


import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    
    @Column(name = "user_name")
    private String userName;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event>  eventList;


    public User() {
    }

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public User(Long id, String userName, List<Event> eventList) {
        this.id = id;
        this.userName = userName;
        this.eventList = eventList;
    }

    public Long getUserId() {
        return id;
    }

    public void setUserId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", userName='" + userName + '\'' +
                ", eventList=" + eventList +
                '}';
    }
}
