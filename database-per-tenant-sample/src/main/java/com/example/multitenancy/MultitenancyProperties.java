
package com.example.multitenancy;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class reads the <code>multitenancy.mtapp</code> node from
 * <code>application.yml</code> file and populates a list of
 * {@link org.springframework.boot.autoconfigure.jdbc.DataSourceProperties}
 * objects, with each instance containing the data source details about the
 * database like url, username, password etc
 * 
 * @author Sunit Katkar
 * @version 1.0
 * @since 1.0 (April 2018)
 */
@Configuration
@ConfigurationProperties("multitenancy.mtapp")
public class MultitenancyProperties {

    private List<DataSourceProperties> dataSourcesProps;

    public List<DataSourceProperties> getDataSources() {
        return this.dataSourcesProps;
    }

    public void setDataSources(List<DataSourceProperties> dataSourcesProps) {
        this.dataSourcesProps = dataSourcesProps;
    }

    public static class DataSourceProperties extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {

        private String tenantId;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }
    }
}
