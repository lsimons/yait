package nl.yait.server.api;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import nl.yait.server.Application;
import nl.yait.server.Configuration;
import nl.yait.server.model.Saying;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldIT {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<>(Application.class, ResourceHelpers.resourceFilePath("config.yml"));

    private final Saying saying = new Saying(1, "Hello, Stranger!");

    @Test
    public void testHelloWorld() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

        assertThat(client.target(
                String.format("http://localhost:%d/hello-world", RULE.getLocalPort()))
                .request().get(Saying.class)).isEqualTo(saying);
    }
}
