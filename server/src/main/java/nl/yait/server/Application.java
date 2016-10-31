package nl.yait.server;

import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.yait.server.api.HelloWorldResource;
import nl.yait.server.checks.TemplateHealthCheck;

@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NM_SAME_SIMPLE_NAME_AS_SUPERCLASS")
public final class Application extends io.dropwizard.Application<Configuration> {
    public static void main(final String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new Java8Bundle());
    }

    @Override
    public void run(final Configuration configuration, final Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}
