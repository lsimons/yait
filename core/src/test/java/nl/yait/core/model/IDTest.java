package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class IDTest {

    private static final ID AN_ID = new ID.Builder()
            .value("urn-x:foo:bar:my-id").build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nonEmpty() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("value");
        expectedException.expectMessage("should not be empty");

        new ID.Builder().value("").build();
    }

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/id.json"), ID.class));
        String serialized = MAPPER.writeValueAsString(AN_ID);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        ID deserialized = MAPPER.readValue(fixture("fixtures/id.json"), ID.class);
        assertThat(deserialized).isEqualTo(AN_ID);
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
    public void numberSupport() {
        long value = 1L + Integer.MAX_VALUE;
        ID id = new ID.Builder().value("" + value).build();
        assertThat(id.asLong()).isEqualTo(value);
        assertThat(id.isLong()).isTrue();

        id = new ID.Builder().value("foo").build();
        assertThat(id.isLong()).isFalse();
        assertThatThrownBy(id::asLong).isInstanceOf(NumberFormatException.class);
    }

    @Test
    public void of() {
        assertThat(ID.of("foo")).isNotNull();
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(AN_ID.compareTo(AN_ID) == 0);
        assertThat(AN_ID.compareTo(ID.of("other")) != 0);
        assertThat(AN_ID.compareTo(null) == 1);
    }
}
