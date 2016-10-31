package nl.yait.server.api;

import com.codahale.metrics.annotation.Timed;
import nl.yait.core.model.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("RestParamTypeInspection") // dropwizard-java8 provides support for Optional
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public final class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(final String template, final String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying helloWorld(final @QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying.Builder()
                .id(counter.incrementAndGet())
                .content(value).build();
    }
}
