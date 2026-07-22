package com.flashbite.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OrderServiceDatabaseIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("flashbite_order")
            .withUsername("flashbite")
            .withPassword("flashbite");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void flywayCreatesOrdersTableWithExpectedFoundationColumns() {
        Integer historyEntries = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM flyway_schema_history WHERE success = TRUE",
                Integer.class
        );
        String deliveryAddressType = jdbcTemplate.queryForObject(
                """
                SELECT udt_name
                FROM information_schema.columns
                WHERE table_name = 'orders' AND column_name = 'delivery_address'
                """,
                String.class
        );
        Integer versionColumnExists = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM information_schema.columns
                WHERE table_name = 'orders' AND column_name = 'version'
                """,
                Integer.class
        );

        assertThat(historyEntries).isEqualTo(1);
        assertThat(deliveryAddressType).isEqualTo("jsonb");
        assertThat(versionColumnExists).isEqualTo(1);
    }
}
