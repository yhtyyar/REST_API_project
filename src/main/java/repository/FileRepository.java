package repository;

import model.Event;
import model.File;

public interface FileRepository extends GenericRepository <File, Long> {

    File create(Long eventId, String filePath, String fileName);
    File update(Long id, Long eventId, String filePath, String fileName);
}
