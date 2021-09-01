package dk.medcom.vdx.example.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.medcom.vdx.example.security.model.AccessToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class AuthorizationClientImpl implements AuthorizationClient {
    private final RestTemplate restTemplate;
    private final String tokenUrl;

    public AuthorizationClientImpl(String tokenUrl, RestTemplate restTemplate) {
        this.tokenUrl = tokenUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public AccessToken authorize(String token) {
        var request = "saml-token=" + token;

        RequestEntity<String> requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(tokenUrl));
        var response = restTemplate.exchange(requestEntity, String.class);

        try {
            return new ObjectMapper().readValue(response.getBody(), AccessToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
