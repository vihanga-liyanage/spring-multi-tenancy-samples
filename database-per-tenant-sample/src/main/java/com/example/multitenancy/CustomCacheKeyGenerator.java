package com.example.multitenancy;

import com.example.util.TenantContextHolder;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class CustomCacheKeyGenerator implements KeyGenerator {

    public Object generate(Object target, Method method, Object... params) {

        return target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + TenantContextHolder.getTenant() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }
}
