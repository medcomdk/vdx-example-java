package dk.medcom.vdx.example.service;

import dk.medcom.vdx.example.security.AuthorizationClient;
import dk.medcom.vdx.example.security.StsClient;
import dk.medcom.vdx.example.security.TokenEncoder;
import dk.medcom.vdx.example.service.model.ExampleServiceOutput;
import dk.medcom.vdx.example.videoapiclient.VideoApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class ExampleServiceImpl implements ExampleService {
    private final Logger logger = LoggerFactory.getLogger(ExampleServiceImpl.class);

    private final StsClient stsClient;
    private final TokenEncoder tokenEncoder;
    private final AuthorizationClient sessionIdExchangeClient;
    private final VideoApiClient videoApiClient;

    public ExampleServiceImpl(StsClient stsClient, AuthorizationClient sessionIdExchangeClient, TokenEncoder tokenEncoder, VideoApiClient videoApiClient) {
        this.stsClient = stsClient;
        this.sessionIdExchangeClient = sessionIdExchangeClient;
        this.tokenEncoder = tokenEncoder;
        this.videoApiClient = videoApiClient;
    }

    @Override
    public ExampleServiceOutput readMeeting(UUID uuid) {
        logger.info(uuid.toString());
        // Get Token from STS
        logger.info("Request token from STS.");
        var response = stsClient.requestToken();
        logger.info("Received token from STS with id {}", response.getId());

        // Encode token
        logger.info("Base64 encode token");
        var encodedToken = tokenEncoder.encode(response);

        // Request access token using received SAML token.
        logger.info("Requesting access token.");
        var accessToken = sessionIdExchangeClient.authorize(encodedToken);

        // Read meeting from VideoAPI
        logger.info("Call VideoAPI using access token.");
        var meeting = videoApiClient.getMeeting(uuid, accessToken.getAccessToken().toString());

        var helloResponse = new ExampleServiceOutput();
        helloResponse.setSubject(meeting.getSubject());
        helloResponse.setUuid(meeting.getUuid());
        helloResponse.setStartTime(OffsetDateTime.ofInstant(meeting.getStartTime().toInstant(), ZoneId.systemDefault()));
        helloResponse.setEndTime(OffsetDateTime.ofInstant(meeting.getEndTime().toInstant(), ZoneId.systemDefault()));
        helloResponse.setDescription(meeting.getDescription());
        helloResponse.setShortId(meeting.getShortId());
        helloResponse.setShortLink(meeting.getShortLink());

        return helloResponse;
    }
}
