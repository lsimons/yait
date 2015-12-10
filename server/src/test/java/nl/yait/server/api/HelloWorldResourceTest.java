package nl.yait.server.api;

import io.dropwizard.testing.junit.ResourceTestRule;
import nl.yait.server.model.Saying;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static nl.yait.server.model.SayingTest.SAYING;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldResourceTest {

    private final static String template = "Hello, %s!";
    private final static String defaultName = "Stranger";

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HelloWorldResource(template, defaultName))
            .build();

    @Test
    public void testHelloWorld() {
        assertThat(getHelloWorldTarget().request().get(Saying.class))
                .isEqualTo(SAYING);
    }

    protected WebTarget getHelloWorldTarget() {
        return resources.client().target("/hello-world");
    }
}
