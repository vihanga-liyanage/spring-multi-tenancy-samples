package com.alonsegal.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        String tenant = LanguageContext.getCurrentLanguage();
        if (tenant == null) {
            return "tenantId2";
        }
        return tenant;

        // get request object
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attr != null) {
//            // find parameter from request
//            String tenantId = attr.getRequest().getParameter("tenantId");
//            return tenantId;
//        } else {
//            // default data source
//            return "tenantId2";
//        }
    }
}