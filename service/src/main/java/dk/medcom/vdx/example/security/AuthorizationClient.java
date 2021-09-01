package dk.medcom.vdx.example.security;

import dk.medcom.vdx.example.security.model.AccessToken;

public interface AuthorizationClient {
    /**
     * Exhange base64 encoded token with session id.
     * @param token Base64 encoded token from STS.
     * @return Session id.
     */
    AccessToken authorize(String token);
}
