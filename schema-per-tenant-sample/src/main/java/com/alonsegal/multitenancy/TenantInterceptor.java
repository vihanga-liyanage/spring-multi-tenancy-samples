package com.alonsegal.multitenancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
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

        // Clear cache if different tenant
        if (TenantContext.previousTenant != null && !TenantContext.previousTenant.equals(tenantID)) {
            clearCache();
            TenantContext.previousTenant = tenantID;
        }
        TenantContext.setCurrentTenant(tenantID);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        TenantContext.clear();
    }

    @Autowired
    private CacheManager cacheManager;

    public void clearCache() {

        if (cacheManager != null) {
            for (String name : cacheManager.getCacheNames()) {
                System.out.print("Clearing cache: " + name);
                cacheManager.getCache(name).clear();            // clear cache by name
            }
        } else {
            System.out.printf("cacheManager is null");
        }
    }
}
