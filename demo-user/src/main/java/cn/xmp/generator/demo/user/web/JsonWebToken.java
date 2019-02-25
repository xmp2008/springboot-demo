package cn.xmp.generator.demo.user.web;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/14
 */

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.entity.AppUser;
import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.jwt.model.AccessToken;
import cn.xmp.generator.demo.user.jwt.model.LoginPara;
import cn.xmp.generator.demo.user.jwt.utils.JwtHelper;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.service.IAppUserService;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import cn.xmp.generator.demo.user.utils.MyUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
public class JsonWebToken {
    private IAppUserService appUserService;

    private Audience audienceEntity;

    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    @ResponseBody
    public Object getAccessToken(@RequestBody LoginPara loginPara) {
        BaseResponse response = new BaseResponse();
        log.info("获取token入参{}", loginPara);
        try {
            //验证用户名密码
            AppUser user = new AppUser();
            user.setName(loginPara.getUserName());
            user.setPassword(loginPara.getPassword());
            log.info("用户登录入参:{}", user.toString());
            BaseResponse userResponse = appUserService.login(user);
            log.info("用户登录返回:{}", userResponse);
            user = (AppUser) userResponse.getDataInfo();
            if (user == null) {
                response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1002.getCode());
                return response;
            }
            //拼装accessToken
            String accessToken = JwtHelper.createJWT(loginPara.getUserName(), String.valueOf(user.getId()),
                    audienceEntity.getClientId(), audienceEntity.getName(),
                    audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());

            //返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccess_token(accessToken);
            accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
            accessTokenEntity.setToken_type("bearer");
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
            response.setDataInfo(accessTokenEntity);
            log.info("获取token返回{}", response);
            return response;

        } catch (Exception ex) {
            log.info("获取token异常:{}", ex);
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1004.getCode());
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public static void main(String[] args) {
        System.out.println(MyUtils.getMD5("666"));
    }
}