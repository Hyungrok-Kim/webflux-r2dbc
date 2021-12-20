package myspringboot.reactive.r2dbc.router;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import myspringboot.reactive.r2dbc.dto.Customer;
import myspringboot.reactive.r2dbc.handler.CustomerHandler;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouteConfig {
    @Autowired
    private CustomerHandler customerHandler;

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/router/customers",
                            produces = {
                                    MediaType.TEXT_EVENT_STREAM_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CustomerHandler.class,
                            beanMethod = "loadCustomersStream",
                            operation = @Operation(
                                    operationId = "loadCustomersStream",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Customer.class // 응답으로 주는게 무슨 타입인지
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/router/customers/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CustomerHandler.class,
                            beanMethod = "findCustomer",
                            operation = @Operation(
                                    operationId = "findCustomer",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Customer.class
                                                    ))
                                            ),
                                            @ApiResponse(responseCode = "404", description = "customer not found with given id")
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id")
                                    }

                            )

                    ),
                    @RouterOperation(
                            path = "/router/customers",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = CustomerHandler.class,
                            beanMethod = "saveCustomer",
                            operation = @Operation(
                                    operationId = "saveCustomer",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "successful operation",
                                                    content = @Content(schema = @Schema(
                                                            implementation = String.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = Customer.class
                                            ))
                                    )

                            )


                    )
            }
    )
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customers",customerHandler::loadCustomersStream)
                .GET("/router/customers/{id}",customerHandler::findCustomer)
                .POST("/router/customers",customerHandler::saveCustomer)
                .build();
    }
}