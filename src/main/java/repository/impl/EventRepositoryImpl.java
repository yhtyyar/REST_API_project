package repository.impl;

import model.Event;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.EventRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class EventRepositoryImpl implements EventRepository {

    private Transaction transaction;
    private final UserRepository userRepository;

    public EventRepositoryImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public Event create(String eventName) {

        Event eventResult = new Event();

        // создаем юзера по умолчанию
        User user = userRepository.create(defaultUser);

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Event event = new Event();
            event.setUser(user);
            event.setEventName(eventName);

            Long id = (Long) session.save(event);

            //для получения генерированного ID
            eventResult = session.get(Event.class, id);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return eventResult;
    }


    @Override
    public Event update(Long id, Long userId, String eventName) {

        Event eventResult = new Event();
        User user = userRepository.update(userId, defaultUser);

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            Event event = session.get(Event.class, id);
            event.setEventName(eventName);
            event.setUser(user);

            session.update(event);
            transaction.commit();

        } catch(HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return eventResult;
    }


    @Override
    public Event getById(Long id) {

        Event event = new Event();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            event = session.get(Event.class, id);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return event;
    }


    @Override
    public void deleteById(Long id) {

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Event event = session.get(Event.class, id);

            session.delete(event);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }


    @Override
    public List<Event> getAll() {

        List<Event> eventList = new ArrayList<>();

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            eventList = session.createQuery("FROM Event", Event.class).list();

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return eventList;
    }
}
