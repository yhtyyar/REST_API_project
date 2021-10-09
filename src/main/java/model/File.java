package model;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;


    @Column(name = "file_path")
    private String filePath;


    @Column(name = "file_name")
    private String fileName;



    public File() {
    }


    public File(Long id, Event event, String filePath, String fileName) {
        this.id = id;
        this.event = event;
        this.filePath = filePath;
        this.fileName = fileName;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", event=" + event + '\'' +
                ", fileName='" + fileName +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
