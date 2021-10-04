package repository.impl;

import model.Event;
import model.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.EventRepository;
import repository.FileRepository;

import java.util.ArrayList;
import java.util.List;

public class FileRepositoryImpl implements FileRepository {

    private Transaction transaction;
    private final EventRepository eventRepository;


    public FileRepositoryImpl() {
        eventRepository = new EventRepositoryImpl();
    }

    @Override
    public File create(Long eventId, String filePath, String fileName) {

        File fileResult = new File();
        Long fileId;

        Event event = eventRepository.getById(eventId);

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            File file = new File();
            file.setEvent(event);
            file.setFilePath(filePath);
            file.setFileName(fileName);

            fileId = (Long) session.save(file);
            fileResult = session.get(File.class, fileId);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return fileResult;
    }

    @Override
    public File update(Long id, Long eventId, String filePath, String fileName) {

        File fileResult = new File();

        Event event = eventRepository.getById(eventId);

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            File file = session.get(File.class, id);

            file.setEvent(event);
            file.setFilePath(filePath);
            file.setFileName(fileName);

            session.update(file);
            fileResult = session.get(File.class, id);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return fileResult;
    }

    @Override
    public File getById(Long id) {

        File file = new File();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            file = session.get(File.class, id);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return file;
    }

    @Override
    public void deleteById(Long id) {

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            File file = session.get(File.class, id);

            session.delete(file);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<File> getAll() {

        List<File> fileList = new ArrayList<>();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            fileList = session.createQuery("FROM File", File.class).list();

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return fileList;
    }

}
