package nl.yait.core.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NamespacesTest {
    @Test
    public final void coreNamespace() throws Exception {
        assertThat(Namespaces.YAIT_CORE).isNotNull();
    }

    @Test
    public final void withKnownPrefix() throws Exception {
        Namespace namespaceWithoutPrefix = Namespace.of(Namespaces.YAIT_CORE.getNamespace());
        Namespace prefixed = Namespaces.withWellKnownPrefix(namespaceWithoutPrefix);
        assertThat(prefixed).isEqualTo(Namespaces.YAIT_CORE);
    }
}
