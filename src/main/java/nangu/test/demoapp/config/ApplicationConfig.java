package nangu.test.demoapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Michal Kluzacek
 *
 * Main application configuration
 */

@Configuration
@EnableTransactionManagement
@Import(SecurityConfig.class)
public class ApplicationConfig {

}
