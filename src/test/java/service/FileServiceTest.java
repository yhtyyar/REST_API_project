package service;

import model.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.FileRepository;
import repository.impl.FileRepositoryImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    private File fileTest;
    private List<File> fileListTest;


    @Mock
    private final FileRepository fileRepo = new FileRepositoryImpl();

    @Mock
    private final FileService fileUnderTest = new FileService(fileRepo);


    @Test
    public void getByIdTest() {
        File file = fileUnderTest.getById(1L);
        assertEquals(fileTest,file);
    }


    @Test
    public void createTest() {
        File file = fileUnderTest.create(1L,"/Test/","TestFile");
        assertEquals(fileTest,file);
    }


    @Test
    public void updateTest() {
        File file = fileUnderTest.update(1L,2L,"/Test/","Test");
        assertEquals(fileTest,file);
    }

}
