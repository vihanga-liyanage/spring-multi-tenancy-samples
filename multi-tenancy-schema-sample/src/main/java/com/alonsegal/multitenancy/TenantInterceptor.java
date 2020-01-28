package com.alonsegal.multitenancy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Alon Segal on 23/03/2017.
 */

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

//    @Value("${jwt.header}")
//    private String tokenHeader;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//
//        String authToken = request.getHeader(this.tokenHeader);
//        // authToken.startsWith("Bearer ")
//        // String authToken = header.substring(7);
//        String tenantId = "tenantId from authToken";//jwtTokenUtil.getTenantIdFromToken(authToken);
//        TenantContext.setCurrentTenant(tenantId);
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(
//            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
//            throws Exception {
//        TenantContext.clear();
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();
        String tenantID = request.getHeader("X-TenantID");
        System.out.println("RequestURI::" + requestURI + " || Search for X-TenantID  :: " + tenantID);
        System.out.println("____________________________________________");
        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        TenantContext.clear();
    }
}
