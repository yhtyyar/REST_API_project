package service;

import model.File;
import repository.FileRepository;
import repository.impl.FileRepositoryImpl;

import java.util.List;

public class FileService {

    private final FileRepository fileRepository;

    public FileService() {
        fileRepository = new FileRepositoryImpl();
    }


    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public File create(Long eventId, String filePath, String fileName) {
        return fileRepository.create(eventId, filePath, fileName);
    }


    public File update(Long id, Long eventId, String filePath, String fileName) {
        return fileRepository.update(id, eventId, filePath, fileName);
    }


    public File getById(Long id) {
        return fileRepository.getById(id);
    }


    public void deleteById (Long id) {
        fileRepository.deleteById(id);
    }


    public List<File> getAll() {
        return fileRepository.getAll();
    }
}
