package travellog.config;


import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfig extends AbstractR2dbcConfiguration {
    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        var options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.DATABASE, "travellog")
                .option(ConnectionFactoryOptions.USER, "")
                .option(ConnectionFactoryOptions.PASSWORD, "")
                .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                //.option(PostgresqlConnectionFactoryProvider.OPTIONS, Map.of("lock_timeout", "30s"))
                .build();
        return ConnectionFactories.get(options);
    }
    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}