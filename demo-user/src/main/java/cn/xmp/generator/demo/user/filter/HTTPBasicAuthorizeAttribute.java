package cn.xmp.generator.demo.user.filter;

import cn.xmp.generator.demo.user.enums.ReturnCodeEnum;
import cn.xmp.generator.demo.user.model.response.BaseResponse;
import cn.xmp.generator.demo.user.utils.BackResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
//@Service
@Slf4j
@SuppressWarnings("restriction")
public class HTTPBasicAuthorizeAttribute implements Filter {

    @Value("${BasicAuthorName}")
    private String name = "test";
    @Value("${BasicAuthorPassword}")
    private String password = "test";

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
                        log.info("Name:{},Password:{}", name, password);
                        if (UserArray != null && UserArray.length == 2) {
                            if (UserArray[0].compareTo(name) == 0
                                    && UserArray[1].compareTo(password) == 0) {
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
