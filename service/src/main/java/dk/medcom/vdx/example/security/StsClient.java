package dk.medcom.vdx.example.security;

import org.apache.cxf.ws.security.tokenstore.SecurityToken;

public interface StsClient {

    SecurityToken requestToken();
}
