package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URI;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public final class NamespaceTest {

    private static final String TEST_NS = "urn:x-test:foo";
    private static final Namespace A_NAMESPACE = new Namespace.Builder()
            .namespace(TEST_NS).build();
    private static final ImmutableNamespace A_NAMESPACE_WITH_PREFIX = new Namespace.Builder()
            .namespace(A_NAMESPACE.getNamespace())
            .defaultPrefix("foo").build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nonEmpty() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should not be empty");

        new Namespace.Builder().namespace("").build();
    }

    @Test
    public void validURI() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("namespace");
        expectedException.expectMessage("should be a valid URI");

        new Namespace.Builder().namespace(" many things are valid uris but this isn't").build();
    }

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/namespace.json"), Namespace.class));
        String serialized = MAPPER.writeValueAsString(A_NAMESPACE);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        Namespace deserialized = MAPPER.readValue(
                fixture("fixtures/namespace.json"), Namespace.class);
        assertThat(deserialized).isEqualTo(A_NAMESPACE);
    }

    @Test
    public void supportsOptionalPrefix() {
        assertFalse(A_NAMESPACE.getDefaultPrefix().isPresent());
        assertTrue(A_NAMESPACE_WITH_PREFIX.getDefaultPrefix().isPresent());
        assertThat(A_NAMESPACE).isNotEqualTo(A_NAMESPACE_WITH_PREFIX);
    }

    @Test
    public void optionalPrefixDoesNotSerialize() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/namespace.json"), Namespace.class));
        String serialized = MAPPER.writeValueAsString(A_NAMESPACE_WITH_PREFIX);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void of() throws Exception {
        assertThat(Namespace.of(TEST_NS)).isEqualTo(A_NAMESPACE);
    }

    @Test
    public void ofWithPrefix() throws Exception {
        assertThat(Namespace.of(TEST_NS, "foo").getDefaultPrefix().get()).isEqualTo("foo");
    }

    @Test
    public void ofURI() throws Exception {
        assertThat(Namespace.of(new URI(TEST_NS))).isEqualTo(A_NAMESPACE);
        assertThat(Namespace.of(TEST_NS, "foo").getDefaultPrefix().get()).isEqualTo("foo");
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(A_NAMESPACE.compareTo(A_NAMESPACE) == 0);
        assertThat(A_NAMESPACE.compareTo(A_NAMESPACE_WITH_PREFIX) == 0);
        assertThat(A_NAMESPACE.compareTo(Namespace.of("urn:x-test:bar")) != 0);
        assertThat(A_NAMESPACE.compareTo(null) == 1);
    }
}
