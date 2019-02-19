package cn.xmp.generator.demo.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/15
 */

@Data
@ConfigurationProperties(prefix = "audience")
public class Audience {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
}
