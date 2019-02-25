# springboot实战整合

## 1.自定义配置文件

### 1.新建配置类Audience

```java
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
```

### 2.yaml文件添加配置信息

```yaml
audience:
  clientId: 098f6bcd4621d373cade4e832627b4f6
  base64Secret: MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=
  name: restapiuser
  expiresSecond: 172800
```

### 3.Controller注入配置

```java
package cn.xmp.generator.demo.user.web;

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/app/appUser")
public class AppUserController {

    private Audience audience;

    @RequestMapping(value = "/getaudience", method = RequestMethod.GET)
    @ResponseBody
    public Object getAudience() {
        BaseResponse response = new BaseResponse();
        response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        response.setDataInfo(audience);
        return response;
    }
}
```

### 4.PostMan测试

`http://localhost:8081/app/appUser/getaudience`

```json
{
    "returnCode": 1000,
    "message": "成功",
    "dataInfo": {
        "clientId": "098f6bcd4621d373cade4e832627b4f6",
        "base64Secret": "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=",
        "name": "restapiuser",
        "expiresSecond": 172800
    }
}```
```

## 2.自定义map,数组,list对象类配置

### 1.新建配置类ConfigProperties

```java
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
```

### 2.添加yaml配置

```yaml
xx:
  test:
    str: strTest
    testArray: 1,2,3,a  #这种对象形式的，只能单独写一个对象去接收，所以无法使用@value注解获取
    listMap:
      - host: weihu01
        port: 10
        active: 9
      - host: weihu02
        port: 11
        active: 8
    listStr:
      - name
      - value
    map:
      a: a
      b: b
```

### 3. Controller注入配置

```java
package cn.xmp.generator.demo.user.web;

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiemopeng
 * @since 2019-01-28
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/app/appUser")
public class AppUserController {

    private ConfigProperties config;

    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    @ResponseBody
    public Object getConfig() {
        BaseResponse response = new BaseResponse();
        response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        response.setDataInfo(config);
        return response;
    }
}
```

### 4.测试接口

`http://localhost:8081/app/appUser/getConfig`

```json
  {
    "returnCode": 1000,
    "message": "成功",
    "dataInfo": {
        "str": "strTest",
        "testArray": [
            "1",
            "2",
            "3",
            "a"
        ],
        "listMap": [
            {
                "host": "weihu01",
                "port": "10",
                "active": "9"
            },
            {
                "host": "weihu02",
                "port": "11",
                "active": "8"
            }
        ],
        "listStr": [
            "name",
            "value"
        ],
        "map": {
            "a": "a",
            "b": "b"
        }
    }
}
```

## 3.Filter实现简单的Http Basic认证

### 1.定义过滤器

```java
package cn.xmp.generator.demo.user.filter;

import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/15
 */


@SuppressWarnings("restriction")
public class HTTPBasicAuthorizeAttribute implements Filter {

    private static String Name = "test";
    private static String Password = "test";

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        BaseResponse resultStatusCode = checkHTTPBasicAuthorize(request);
        if (resultStatusCode.getReturnCode() != ReturnCodeEnum.CODE_1000.getCode()) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            ObjectMapper mapper = new ObjectMapper();

            httpResponse.getWriter().write(mapper.writeValueAsString(resultStatusCode));
            return;
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    private BaseResponse checkHTTPBasicAuthorize(ServletRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String auth = httpRequest.getHeader("Authorization");
            if ((auth != null) && (auth.length() > 6)) {
                String HeadStr = auth.substring(0, 5).toLowerCase();
                if (HeadStr.compareTo("basic") == 0) {
                    auth = auth.substring(6, auth.length());
                    String decodedAuth = getFromBASE64(auth);
                    if (decodedAuth != null) {
                        String[] UserArray = decodedAuth.split(":");

                        if (UserArray != null && UserArray.length == 2) {
                            if (UserArray[0].compareTo(Name) == 0
                                    && UserArray[1].compareTo(Password) == 0) {
                                response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
                                return response;
                            }
                        }
                    }
                }
            }
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1001.getCode());
            return response;
        } catch (Exception ex) {
            response = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1001.getCode());
            return response;
        }

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

}
```

新增HTTPBasicAuthorizeAttribute.java

如果请求的Header中存在Authorization: Basic 头信息，且用户名密码正确，则继续原来的请求，否则返回没有权限的错误信息

### 2.注册过滤器

在SpringRestApplication类中注册过滤器，给/app/appUser/*都加上http basic认证过滤器

```java
package cn.xmp.generator.demo.user;

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.filter.HTTPBasicAuthorizeAttribute;
import cn.xmp.generator.demo.user.filter.HTTPBearerAuthorizeAttribute;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@MapperScan("cn.xmp.generator.demo.user.mapper*")
@SpringBootApplication
@EnableConfigurationProperties(Audience.class)
public class SaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaApplication.class, args);

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/app/appUser/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}
```

### 3.接口测试

代码中固定用户名密码都为test(可以在yml配置)，所以对接口进行请求时，需要添加以下认证头信息

Authorization: Basic dGVzdDp0ZXN0

dGVzdDp0ZXN0 为 test:test 经过base64编码后的结果

`http://localhost:8081/app/appUser/getConfig`

未添加认证信息或者认证信息错误，返回没有权限的错误信息

![1550657537251](C:\Users\asus\AppData\Local\Temp\1550657537251.png)

正确添加认证信息

![1550657609571](C:\Users\asus\AppData\Local\Temp\1550657609571.png)

## 4.Filter实现使用JWT进行接口认证

### 1.添加依赖库jjwt

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.6.0</version>
</dependency>
```

### 2.添加登录request请求类

```java
/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/14
 */
@Data
public class LoginPara {
    private String userName;
    private String password;
}
```

### 3.添加生产jwt的token和解析token的工具类

```java
package cn.xmp.generator.demo.user.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/14
 */

public class JwtHelper {
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createJWT(String name, String userId,
                                   String audience, String issuer, long TTLMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("unique_name", name)
                .claim("userid", userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }
}
```

### 4.获取jwt-token返回的封装类

```java
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
```

### 5.添加获取token的controller

```java
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
}
```

### 6.添加校验token的filter

```java
package cn.xmp.generator.demo.user.filter;

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.jwt.utils.JwtHelper;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * <p>
 *
 * @author xiemopeng
 * @since 2019/2/15
 */


public class HTTPBearerAuthorizeAttribute implements Filter {
    @Autowired
    private Audience audienceEntity;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        BaseResponse baseResponse = new BaseResponse();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String auth = httpRequest.getHeader("AccessToken");
        if ((auth != null) && (auth.length() > 7)) {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0) {

                auth = auth.substring(7, auth.length());
                if (JwtHelper.parseJWT(auth, audienceEntity.getBase64Secret()) != null) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1010.getCode());
        httpResponse.getWriter().write(mapper.writeValueAsString(baseResponse));
        return;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
```

### 7.启动入口注册filter

```java
package cn.xmp.generator.demo.user;

import cn.xmp.generator.demo.user.config.Audience;
import cn.xmp.generator.demo.user.filter.HTTPBasicAuthorizeAttribute;
import cn.xmp.generator.demo.user.filter.HTTPBearerAuthorizeAttribute;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@MapperScan("cn.xmp.generator.demo.user.mapper*")
@SpringBootApplication
@EnableConfigurationProperties(Audience.class)
public class SaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaApplication.class, args);

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/app/appUser/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();
        registrationBean.setFilter(httpBearerFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/app/appUser/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}
```

### 8.测试

