//package dk.kvalitetsit.hello.controller;
//
//import dk.kvalitetsit.hello.api.HelloRequest;
//import dk.kvalitetsit.hello.controller.exception.BadRequestException;
//import dk.kvalitetsit.hello.service.ExampleService;
//import dk.kvalitetsit.hello.service.model.HelloServiceInput;
//import dk.kvalitetsit.hello.service.model.HelloServiceOutput;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mockito;
//
//import java.time.ZonedDateTime;
//import java.util.UUID;
//
//import static junit.framework.TestCase.assertNotNull;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.times;
//
//public class HelloControllerTest {
//    private ExampleController helloController;
//    private ExampleService exampleService;
//
//    @Before
//    public void setup() {
//        exampleService = Mockito.mock(ExampleService.class);
//
//        helloController = new ExampleController(exampleService);
//    }
//
//    @Test
//    public void testCallController() {
//        var input = new HelloRequest();
//        input.setName(UUID.randomUUID().toString());
//
//        var expectedDate = ZonedDateTime.now();
//        Mockito.when(exampleService.readMeeting(Mockito.any())).then(a -> {
//            HelloServiceOutput output = new HelloServiceOutput();
//            output.setNow(expectedDate);
//            output.setName(a.getArgument(0, HelloServiceInput.class).getName());
//            return output;
//        });
//
//        var result = helloController.hello(input);
//
//        assertNotNull(result);
//        assertEquals(input.getName(), result.getName());
//        assertEquals(expectedDate, result.getNow());
//
//        var inputArgumentCaptor = ArgumentCaptor.forClass(HelloServiceInput.class);
//        Mockito.verify(exampleService, times(1)).readMeeting(inputArgumentCaptor.capture());
//
//        assertNotNull(inputArgumentCaptor.getValue());
//        assertEquals(input.getName(), inputArgumentCaptor.getValue().getName());
//    }
//
//    @Test(expected = BadRequestException.class)
//    public void testNullInput() {
//        helloController.hello(null);
//    }
//}
