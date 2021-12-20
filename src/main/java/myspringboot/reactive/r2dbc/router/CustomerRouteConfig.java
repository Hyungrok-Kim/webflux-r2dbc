package myspringboot.reactive.r2dbc.router;

import myspringboot.reactive.r2dbc.handler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouteConfig {
    @Autowired
    private CustomerHandler customerHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customers", customerHandler::loadCustomersStream)
                .GET("/router/customers/{id}", customerHandler::findCustomer)
                .build();
    }
}
