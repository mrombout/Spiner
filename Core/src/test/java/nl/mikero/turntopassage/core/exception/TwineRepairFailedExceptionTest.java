package nl.mikero.turntopassage.core.exception;

import nl.mikero.turntopassage.core.TwineArchiveParser;
import nl.mikero.turntopassage.core.model.TwStoriesdata;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TwineRepairFailedExceptionTest {

    @Test
    public void constructor_WithMessageNoCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";

        // Act
        TwineRepairFailedException exception = new TwineRepairFailedException(msg, null);

        // Assert
        assertEquals(msg, exception.getMessage());
    }

    @Test
    public void constructor_NoMessageWithCause_MessageIsProperlySet() {
        // Arrange
        Exception e = mock(Exception.class);

        // Act
        TwineRepairFailedException exception = new TwineRepairFailedException(null, e);

        // Assert
        assertEquals(e, exception.getCause());
    }

    @Test
    public void constructor_WithMessageWithCause_MessageIsProperlySet() {
        // Arrange
        String msg = "Lorum ipsum dolor sit amet.";
        Exception e = mock(Exception.class);

        // Act
        TwineRepairFailedException exception = new TwineRepairFailedException(msg, e);

        // Assert
        assertEquals(msg, exception.getMessage());
        assertEquals(e, exception.getCause());
    }

}
