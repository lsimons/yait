package nl.yait.server.api;

import io.dropwizard.java8.jersey.OptionalMessageBodyWriter;
import io.dropwizard.java8.jersey.OptionalParamFeature;
import io.dropwizard.testing.junit.ResourceTestRule;
import nl.yait.core.model.Saying;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldResourceTest {

    private static final String TEMPLATE = "Hello, %s!";
    private static final String DEFAULT_NAME = "Stranger";
    private static final Saying SAYING = new Saying.Builder()
            .id(1).content("Hello, Stranger!").build();

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new HelloWorldResource(TEMPLATE, DEFAULT_NAME))
            .addProvider(OptionalMessageBodyWriter.class)
            .addProvider(OptionalParamFeature.class)
            .build();

    @Test
    public final void testHelloWorld() {
        assertThat(getHelloWorldTarget().request().get(Saying.class))
                .isEqualTo(SAYING);
    }

    @SuppressWarnings("checkstyle:designforextension")
    protected WebTarget getHelloWorldTarget() {
        return RESOURCES.client().target("/hello-world");
    }
}
