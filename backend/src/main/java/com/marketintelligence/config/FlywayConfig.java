package com.marketintelligence.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configures Flyway explicitly so database migrations run on Spring Boot 4. */
@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/market-migration")
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .outOfOrder(true)
                .load();
    }
}
