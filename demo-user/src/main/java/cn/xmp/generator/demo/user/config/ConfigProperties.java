package cn.xmp.generator.demo.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/1/9
 */
@Data
@Component
@ConfigurationProperties(prefix = "xx.test")
public class ConfigProperties {

    private String str;

    private String[] testArray;

    private List<Map<String, String>> listMap;

    private List<String> listStr;

    private Map<String, String> map;

}