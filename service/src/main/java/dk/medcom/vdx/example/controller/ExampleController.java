package dk.medcom.vdx.example.controller;

import dk.medcom.vdx.example.api.ExampleResponse;
import dk.medcom.vdx.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ExampleController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);
    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping(value = "/v1/{uuid}")
    public ResponseEntity<ExampleResponse> hello(@PathVariable("uuid") UUID uuid) {
        logger.info("Enter GET with uuid {}.", uuid);

        var serviceResponse = exampleService.readMeeting(uuid);

        var helloResponse = new ExampleResponse();
        helloResponse.setSubject(serviceResponse.getSubject());
        helloResponse.setUuid(serviceResponse.getUuid());
        helloResponse.setStartTime(serviceResponse.getStartTime());
        helloResponse.setEndTime(serviceResponse.getEndTime());
        helloResponse.setDescription(serviceResponse.getDescription());
        helloResponse.setShortId(serviceResponse.getShortId());
        helloResponse.setShortLink(serviceResponse.getShortLink());

        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .body(helloResponse);
    }
}
