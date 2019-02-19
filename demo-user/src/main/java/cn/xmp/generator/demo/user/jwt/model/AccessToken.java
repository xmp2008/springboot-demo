package cn.xmp.generator.demo.user.jwt.model;

import lombok.Data;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/14
 */
@Data
public class AccessToken {
    private String access_token;
    private String token_type;
    private long expires_in;
}
