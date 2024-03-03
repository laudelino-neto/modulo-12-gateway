package br.com.unicesumar.modulo12.config.route;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Value("${url-gateway-clientes}")
	private String urlGatewayCliente;
	
	@Value("${url-gateway-fornecedores}")
	private String urlGatewayFornecedor;

	@Bean
	public RouteLocator redirectRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("toClienteAPI", r -> 
					r.path(urlGatewayCliente + "/**")			
		            .filters(f -> f.stripPrefix(1)) 
					.uri("lb://MODULO-12-CLIENTES-API"))
				.route("toFornecedorAPI", r -> 
					r.path(urlGatewayFornecedor + "/**")			
		            .filters(f -> f.stripPrefix(1))		            
					.uri("lb://MODULO-12-FORNECEDORES-API"))
				.build();
	}
	
}
