package br.com.unicesumar.modulo12.config.filter;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LogFilter implements Serializable, GlobalFilter, Ordered{

	private static final long serialVersionUID = 1L;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		
		StringBuilder logRequest = new StringBuilder();
		logRequest.append("[").append(LocalDateTime.now()).append("] ");		
		logRequest.append(" REQUEST => URI: ").append(exchange.getRequest().getPath());
		logRequest.append(" Status: ").append(exchange.getResponse().getStatusCode());
		
		System.out.println(logRequest);
		
		return chain.filter(exchange.mutate().request(request).build()).then(
			Mono.fromRunnable(() -> {
	    			
				ServerHttpResponse response = exchange.getResponse();	    				    			    			    
	    			
	    		StringBuilder logResponse = new StringBuilder();
	    		logResponse.append("[").append(LocalDateTime.now()).append("]");	    			
	    		logResponse.append(" RESPONSE => URI: ").append(exchange.getRequest().getPath());    			
	    		logResponse.append(" Status: ").append(response.getStatusCode());

	    		System.out.println(logResponse);
        	})
	    );
	}
	
	@Override
	public int getOrder() {
		return -1;
	}

}
