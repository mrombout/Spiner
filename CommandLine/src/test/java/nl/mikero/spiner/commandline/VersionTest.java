package nl.mikero.spiner.commandline;

import nl.mikero.spiner.commandline.service.GradleVersionService;
import nl.mikero.spiner.commandline.service.VersionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VersionTest {
    private VersionService service;

    @Before
    public void setUp() {
        service = new GradleVersionService();
    }

    @Test
    public void get_Default_DoesNotReturnUNKNOWN() {
        // Arrange

        // Act
        String result = service.get();

        // Assort
        Assert.assertNotEquals("UNKNOWN", result);
    }
}
