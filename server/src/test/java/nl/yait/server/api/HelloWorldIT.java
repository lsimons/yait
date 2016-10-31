package nl.yait.server.api;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import nl.yait.server.Application;
import nl.yait.server.Configuration;
import org.junit.ClassRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public final class HelloWorldIT extends HelloWorldResourceTest {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE = new DropwizardAppRule<>(
            Application.class, ResourceHelpers.resourceFilePath("config.yml"));

    protected WebTarget getHelloWorldTarget() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
        return client.target(
                String.format("http://localhost:%d/hello-world", RULE.getLocalPort()));
    }
}
