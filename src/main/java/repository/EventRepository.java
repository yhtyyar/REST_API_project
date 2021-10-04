package repository;

import model.Event;



public interface EventRepository extends GenericRepository<Event, Long> {

    Event create (String eventName);
    Event update(Long id, Long userId, String eventName);
}
