package service;

import model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.EventRepository;
import repository.impl.EventRepositoryImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private Event eventTest;
    private List<Event> eventListTest;


    @Mock
    private final EventRepository eventRepo = new EventRepositoryImpl();

    @Mock
    private final EventService eventUnderTest = new EventService(eventRepo);


    @Test
    public void getByIdTest() {
        Event event = eventUnderTest.getById(1L);
        assertEquals(eventTest,event);
    }


    @Test
    public void createTest() {
        Event event = eventUnderTest.create("Test");
        assertEquals(eventTest, event);
    }


    @Test
    public void updateTest() {
        Event event = eventUnderTest.update(1L,2L,"Test");
        assertEquals(eventTest, event);
    }

}
