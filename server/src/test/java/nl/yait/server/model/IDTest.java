package nl.yait.server.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IDTest {

    public final static ID AN_ID = new ID.Builder()
            .value("urn-x:foo:bar:my-id").build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/id.json"), ID.class));
        String serialized = MAPPER.writeValueAsString(AN_ID);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        ID expected = MAPPER.readValue(fixture("fixtures/id.json"), ID.class);
        assertThat(expected).isEqualTo(AN_ID);
    }

    @Test
    public void uriSupport() throws Exception {
        ID id = new ID.Builder().value("urn:my:uri").build();
        URI uri = new URI(id.getValue());
        assertThat(id.asURI()).isEqualTo(uri);
        assertThat(id.isURI()).isTrue();

        id = new ID.Builder().value(" not a uri ").build();
        assertThat(id.isURI()).isFalse();
        assertThatThrownBy(id::asURI).isInstanceOf(URISyntaxException.class);
    }

    @Test
    public void urlSupport() throws Exception {
        ID id = new ID.Builder().value("http://example.com/#this").build();
        URL url = new URL(id.getValue());
        assertThat(id.asURL()).isEqualTo(url);
        assertThat(id.isURL()).isTrue();

        id = new ID.Builder().value(" not a url ").build();
        assertThat(id.isURL()).isFalse();
        assertThatThrownBy(id::asURL).isInstanceOf(MalformedURLException.class);
    }

    @Test
    public void numberSupport() throws Exception {
        long value = 1L + Integer.MAX_VALUE;
        ID id = new ID.Builder().value("" + value).build();
        assertThat(id.asLong()).isEqualTo(value);
        assertThat(id.isLong()).isTrue();
    }
}
