package dk.medcom.vdx.example.videoapiclient;

import dk.medcom.vdx.example.videoapiclient.model.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

public class VideoApiClientImpl implements VideoApiClient {
    private final Logger logger = LoggerFactory.getLogger(VideoApiClientImpl.class);
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public VideoApiClientImpl(String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Meeting getMeeting(UUID uuid, String sessionId) {
        logger.info("Reading meeting from video api with UUID {} and using session id {}.", uuid, sessionId);
        RequestEntity<Void> requestEntity = RequestEntity
                .get(URI.create(baseUrl + "/meetings/" + uuid.toString()))
                .header("Authorization", "Holder-of-key " + sessionId)
                .build();

        return restTemplate.exchange(requestEntity, Meeting.class).getBody();
    }
}
