package org.restjwtdemo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestRequestInterceptor implements ClientHttpRequestInterceptor {
    @Value("jwt")
    private String jwtToken;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders().add("Authorization", "Bearer " + this.jwtToken);
        return execution.execute(request, body);
    }
}
