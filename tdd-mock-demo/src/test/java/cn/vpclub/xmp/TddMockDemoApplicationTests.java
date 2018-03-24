package cn.vpclub.xmp;

import cn.vpclub.xmp.dao.PersonDao;
import cn.vpclub.xmp.entity.Person;
import cn.vpclub.xmp.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TddMockDemoApplicationTests {
    @Autowired
    private PersonDao mockDao;
    @Autowired
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        //模拟PersonDao对象
        mockDao = mock(PersonDao.class);
        when(mockDao.getPerson(1L)).thenReturn(new Person(1L, "Person1"));
        when(mockDao.update(isA(Person.class))).thenReturn(true);

        personService = new PersonService(mockDao);
    }

    @Test
    public void contextLoads() {
        boolean result = personService.update(2L, "new name");
        assertFalse("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1L));
        //验证是否执行过一次update
        verify(mockDao, never()).update(isA(Person.class));
    }

}
