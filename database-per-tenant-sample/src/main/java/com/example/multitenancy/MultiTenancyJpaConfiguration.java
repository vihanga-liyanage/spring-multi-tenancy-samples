package com.example.multitenancy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.multitenancy.MultitenancyProperties.DataSourceProperties;

@Configuration
@EnableConfigurationProperties({MultitenancyProperties.class, JpaProperties.class})
@EnableTransactionManagement
public class MultiTenancyJpaConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private MultitenancyProperties multitenancyProperties;

    /**
     * Builds a map of all data sources defined in the application.yml file
     */
    @Primary
    @Bean(name = "dataSourcesMtApp")
    public Map<String, DataSource> dataSourcesMtApp() {

        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceProperties dsProperties : this.multitenancyProperties.getDataSources()) {

            DataSourceBuilder factory = DataSourceBuilder.create().url(dsProperties.getUrl()).username(
                    dsProperties.getUsername()).password(dsProperties.getPassword()).driverClassName(
                    dsProperties.getDriverClassName());

            result.put(dsProperties.getTenantId(), factory.build());
        }
        return result;
    }

    /**
     * Auto wires the data sources so that they can be used by the Spring JPA to
     * access the database
     */
    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {

        return new DataSourceBasedMultiTenantConnectionProviderImpl();
    }

    /**
     * Since this is a multi-tenant application, Hibernate requires that the current
     * tenant identifier is resolved for use with
     */
    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {

        return new CurrentTenantIdentifierResolverImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        Map<String, Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        hibernateProps.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
        hibernateProps.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);

        // No dataSource is set to resulting entityManagerFactoryBean
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setPackagesToScan("com.example");
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setJpaPropertyMap(hibernateProps);

        return result;
    }

    /**
     * Interface used to interact with the entity manager factory for the persistence unit.
     */
    @Bean
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {

        return entityManagerFactoryBean.getObject();
    }

    /**
     * Creates a new
     * {@link org.springframework.orm.jpa.JpaTransactionManager#JpaTransactionManager(EntityManagerFactory emf)}
     * instance.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {

        return new CustomCacheKeyGenerator();
    }
}