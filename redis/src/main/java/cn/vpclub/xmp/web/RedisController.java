package cn.vpclub.xmp.web;


import cn.vpclub.xmp.entity.User;
import cn.vpclub.xmp.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
//	@Autowired
//	UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/redis/string", method = RequestMethod.GET)
    public void insertString() {
        stringRedisTemplate.opsForValue().set("stringKey", "stringValue");
    }

    @RequestMapping(value = "/redis/string/object", method = RequestMethod.GET)
    public void insertStringObject() {
        User user = new User();
        user.setId(1L);
        user.setAge(20);
        user.setName("xiaoming");
        redisTemplate.opsForValue().set("stringKeyObject", user);
    }

    @RequestMapping(value = "/redis/string/object/get", method = RequestMethod.GET)
    public String getStringObject() {
        User user = (User) redisTemplate.opsForValue().get("stringKeyObject");
        return JsonUtil.objectToJson(user);
    }

}
