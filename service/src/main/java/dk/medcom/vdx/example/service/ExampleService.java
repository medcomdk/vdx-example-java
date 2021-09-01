package dk.medcom.vdx.example.service;

import dk.medcom.vdx.example.service.model.ExampleServiceOutput;

import java.util.UUID;

public interface ExampleService {
    ExampleServiceOutput readMeeting(UUID input);
}
