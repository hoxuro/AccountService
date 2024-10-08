package com.accountmicroservice.account.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ServiceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Llamando al Interceptor");
        logger.info (
                "PRE-HANDLE" +
                "REQUEST:"
                        + "Método: " + request.getMethod()
                        + "Uri: " + request.getRequestURI()
                        + "Query params" + request.getQueryString()
                        + "Ip de origen: " + getRemoteAddr(request)

            );
        response.setHeader("account-request-reviewed", "true");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("POST HANDLE : this may happends after completion");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("AFTER COMPLETION: his happends after completion");
    }

      private String getRemoteAddr(HttpServletRequest request) {

        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            logger.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
