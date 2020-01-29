package com.example.multitenancy;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import com.example.util.TenantContextHolder;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT_ID = "tenant_1";

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.context.spi.CurrentTenantIdentifierResolver#
     * resolveCurrentTenantIdentifier()
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        // The tenant is stored in a ThreadLocal before the end user's login information
        // is submitted for spring security authentication mechanism. Refer to
        // CustomAuthenticationFilter
        String tenant = TenantContextHolder.getTenant();
        return StringUtils.isNotBlank(tenant) ? tenant : DEFAULT_TENANT_ID;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.context.spi.CurrentTenantIdentifierResolver#
     * validateExistingCurrentSessions()
     */
    @Override
    public boolean validateExistingCurrentSessions() {

        return true;
    }
}
