package nl.yait.core.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NamespacesTest {
    @Test
    public void coreNamespace() throws Exception {
        assertThat(Namespaces.YAIT_CORE).isNotNull();
    }
}
