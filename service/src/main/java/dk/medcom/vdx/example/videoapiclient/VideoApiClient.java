package dk.medcom.vdx.example.videoapiclient;

import dk.medcom.vdx.example.videoapiclient.model.Meeting;

import java.util.UUID;

public interface VideoApiClient {
    Meeting getMeeting(UUID uuid, String sessionId);
}
