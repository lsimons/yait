package nl.yait.server.api;

import io.dropwizard.testing.junit.ResourceTestRule;
import nl.yait.server.model.Saying;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldResourceTest {

    private final static String template = "Hello, %s!";
    private final static String defaultName = "Stranger";

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HelloWorldResource(template, defaultName))
            .build();

    private final Saying saying = new Saying(1, "Hello, Stranger!");

    @Test
    public void testHelloWorld() {
        assertThat(resources.client().target("/hello-world").request().get(Saying.class))
                .isEqualTo(saying);
    }
}
