package dk.medcom.vdx.example.security;

import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.STSClient;

public class StsClientImpl implements StsClient {
    private final STSClient client;

    private final String audience;

    public StsClientImpl(STSClient client, String audience) {
        this.client = client;
        this.audience = audience;
    }

    @Override
    public SecurityToken requestToken() {
        try {
            return client.requestSecurityToken(audience);
        } catch (Exception e) {
            throw new RuntimeException("Error requesting token from STS.", e);
        }
    }
}
