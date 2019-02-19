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
public class LoginPara {
    private String clientId;
    private String userName;
    private String password;
    private String captchaCode;
    private String captchaValue;
}
