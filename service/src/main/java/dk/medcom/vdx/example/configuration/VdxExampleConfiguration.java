package dk.medcom.vdx.example.configuration;

import dk.medcom.vdx.example.security.*;
import dk.medcom.vdx.example.service.ExampleService;
import dk.medcom.vdx.example.service.ExampleServiceImpl;
import dk.medcom.vdx.example.videoapiclient.VideoApiClient;
import dk.medcom.vdx.example.videoapiclient.VideoApiClientImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class VdxExampleConfiguration {
    private final Logger logger = LoggerFactory.getLogger(VdxExampleConfiguration.class);

    @Bean
    public ExampleService exampleService(StsClient vdxStsClient, AuthorizationClient authorizationClient, TokenEncoder tokenEncoder, VideoApiClient videoApiClient) {
        return new ExampleServiceImpl(vdxStsClient, authorizationClient, tokenEncoder, videoApiClient);
    }

    @Bean
    public VideoApiClient videoApiClient(@Value("${service.url}")String serviceUrl, RestTemplate restTemplate) {
        return new VideoApiClientImpl(serviceUrl, restTemplate);
    }

    // Create an REST template that can handle mTLS.
    @Bean
    public RestTemplate restTemplate(KeyStore keyStore, @Value("${keystore.password}")String keystorePassword) throws Exception {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keystorePassword.toCharArray());

        KeyManager[] managers = kmf.getKeyManagers();

        var sslContext = SSLContext.getInstance("TLS");
        sslContext.init(managers, null, SecureRandom.getInstanceStrong());

        var httpClient = HttpClientBuilder
                .create()
                .setSSLContext(sslContext)
                .build();

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);

        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public KeyStore getKeyStore(@Value("${keystore.path}")String keystore, @Value("${keystore.password}")String keystorePassword) throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        try (var keystoreInputStream = new FileInputStream(keystore)) {
            ks.load(keystoreInputStream, keystorePassword.toCharArray());
        }

        return ks;
    }

    @Bean
    public TokenEncoder tokenEncoder() {
        return new TokenEncoder();
    }

    @Bean
    public AuthorizationClient authorizationClient(@Value("${service.url}") String serviceUrl, RestTemplate restTemplate) {
        return new AuthorizationClientImpl(serviceUrl + "/token", restTemplate);
    }

    @Bean
    public STSClient stsClient(@Value("${sts.url.wsdl}")String stsWsdlUrl, ApplicationContext applicationContext, @Value("${sts.properties}")String propertyLocation) {
        logger.info("STS WSDL url: " + stsWsdlUrl);
        Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        STSClient stsClient = new STSClient(bus);

        stsClient.setWsdlLocation(stsWsdlUrl);
        stsClient.setServiceName("{http://docs.oasis-open.org/ws-sx/ws-trust/200512/}SecurityTokenService");
        stsClient.setEndpointName("{http://docs.oasis-open.org/ws-sx/ws-trust/200512/}STS_Port");
        stsClient.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
        stsClient.setKeyType("http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey");

        Map<String, Object> properties = new HashMap<>();
        properties.put("ws-security.signature.properties", propertyLocation);
        properties.put("ws-security.sts.token.properties", propertyLocation);

        stsClient.setProperties(properties);

        return stsClient;
    }

    @Bean
    public StsClient vdxStsClient(STSClient stsClient) {
        return new StsClientImpl(stsClient, "urn:medcom:videoapi");
    }
}
