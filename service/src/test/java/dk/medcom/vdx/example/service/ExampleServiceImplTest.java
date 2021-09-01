//package dk.kvalitetsit.hello.service;
//
//import dk.kvalitetsit.hello.service.model.HelloServiceInput;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.UUID;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class ExampleServiceImplTest {
//    private ExampleService exampleService;
//
//    @Before
//    public void setup() {
//        exampleService = new ExampleServiceImpl();
//    }
//
//    @Test
//    public void testValidInput() {
//        var input = new HelloServiceInput();
//        input.setName(UUID.randomUUID().toString());
//
//        var result = exampleService.readMeeting(input);
//        assertNotNull(result);
//        assertNotNull(result.getNow());
//        assertEquals(input.getName(), result.getName());
//    }
//}
