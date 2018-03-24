package cn.vpclub.xmp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2018/3/24
 */
@SpringBootTest
@Slf4j
public class MockitoTest {

    @Test
    public void mockitoTest() {
        //创建mock对象,参数可以是类也可以是接口
        List<String> stringList = Mockito.mock(List.class);
        //设置预期返回值
        Mockito.when(stringList.get(0)).thenReturn("hello");
        //验证预期结果
        String result = stringList.get(0);
        log.info("预期结果 : {}", result);
        Assert.assertEquals("hello", result);

    }
}
