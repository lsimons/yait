package nl.yait.server.checks;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public final class TemplateHealthCheckTest {
    @Test
    public void templateHealthy() throws Exception {
        assertTrue(new TemplateHealthCheck("Hello, %s!").check().isHealthy());
    }

    @Test
    public void templateUnhealthy() throws Exception {
        assertFalse(new TemplateHealthCheck("Missing interpolation").check().isHealthy());
    }
}
