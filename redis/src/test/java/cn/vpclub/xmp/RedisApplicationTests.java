package cn.vpclub.xmp;

import cn.vpclub.xmp.entity.User;
import cn.vpclub.xmp.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String,User> redisTemplate;

    @Test
    public void stringTest() {
        stringRedisTemplate.opsForValue().set("stringKey", "你好");
        Assert.assertEquals("你好", stringRedisTemplate.opsForValue().get("stringKey"));
    }

    @Test
    public void objectStringTest() {

        // 保存对象
        User user = new User(1L, "superMan", 20);
        stringRedisTemplate.opsForValue().set(user.getName(), JsonUtil.objectToJson(user));
        user = new User(1L, "蝙蝠侠", 30);
        stringRedisTemplate.opsForValue().set(user.getName(), JsonUtil.objectToJson(user));
        user = new User(1L, "蜘蛛侠", 40);
        stringRedisTemplate.opsForValue().set(user.getName(), JsonUtil.objectToJson(user));
        Assert.assertEquals(20, JsonUtil.jsonToObject(stringRedisTemplate.opsForValue().get("superMan"),User.class).getAge().longValue());
        Assert.assertEquals(30,JsonUtil.jsonToObject(stringRedisTemplate.opsForValue().get("蝙蝠侠"),User.class).getAge().longValue());
        Assert.assertEquals(40, JsonUtil.jsonToObject(stringRedisTemplate.opsForValue().get("蜘蛛侠"),User.class).getAge().longValue());

    }

    @Test
    public void objectTest() {

        // 保存对象
        User user = new User(1L, "超人", 20);
        redisTemplate.opsForValue().set(user.getName(), user);
        user = new User(1L, "蝙蝠侠", 30);
        redisTemplate.opsForValue().set(user.getName(), user);
        user = new User(1L, "蜘蛛侠", 40);
        redisTemplate.opsForValue().set(user.getName(), user);
        Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().longValue());
        Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
        Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());

    }

}
