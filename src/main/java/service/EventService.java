package service;

import model.Event;
import repository.EventRepository;
import repository.impl.EventRepositoryImpl;

import java.util.List;


public class EventService {

    private final EventRepository eventRepository;


    public EventService() {
       eventRepository = new EventRepositoryImpl();
    }


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public Event create(String eventName) {
        return eventRepository.create(eventName);
    }


    public Event update(Long id, Long userId, String eventName) {
        return eventRepository.update(id, userId, eventName);
    }


    public Event getById (Long id) {
        return eventRepository.getById(id);
    }


    public void deleteById (Long id) {
        eventRepository.deleteById(id);
    }


    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
