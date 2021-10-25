package service;

import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    private User userTest;
    private List<User> userListTest;


    @Mock
    private final UserRepository userRepo = new UserRepositoryImpl();

    @Mock
    private final UserService userUnderTest = new UserService(userRepo);


    @Test
    public void getByIdTest() {
        User user = userUnderTest.getById(1L);
        assertEquals(userTest,user);
    }


    @Test
    public void createTest() {
        User user = userUnderTest.create("Pavel");
        assertEquals(userTest,user);
    }


    @Test
    public void updateTest() {
        User user = userUnderTest.update(1L,"Pavel");
        assertEquals(userTest,user);
    }

}
