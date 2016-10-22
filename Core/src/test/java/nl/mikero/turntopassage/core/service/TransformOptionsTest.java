package nl.mikero.turntopassage.core.service;

import nl.mikero.turntopassage.core.model.XtwMetadata;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Metadata;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransformOptionsTest {

    @Test( expected = NullPointerException.class )
    public void fromXtwMetadata_NullMetadata_ThrowsNullPointerException() {
        // Arrange

        // Act
        TransformOptions transformOptions = TransformOptions.fromXtwMetadata(null);

        // Assert
    }

    @Test
    public void fromXtwMetadata_ValidMetadata_ReturnsTransformOptions() {
        // Arrange
        String title = "Alice in Wonderland";
        String creator = "Lewis Carroll";
        String subject = "subject";
        String description = "description";
        String publisher = "publisher";
        String contributor = "contributor";
        String date = "date";
        String format = "text/html";
        XtwMetadata.Identifier identifier = new XtwMetadata.Identifier();
        String identifierScheme = "ISBN";
        String identifierValue = "39214097";
        identifier.setScheme(identifierScheme);
        identifier.setValue(identifierValue);
        String language = "en";
        String rights = "rights";


        XtwMetadata xtwMetadata = new XtwMetadata();
        xtwMetadata.getTitle().add(title);
        xtwMetadata.getCreator().add(creator);
        xtwMetadata.getSubject().add(subject);
        xtwMetadata.getDescription().add(description);
        xtwMetadata.getPublisher().add(publisher);
        xtwMetadata.getContributor().add(contributor);
        xtwMetadata.getDate().add(date);
        xtwMetadata.setFormat(format);
        xtwMetadata.getIdentifier().add(identifier);
        xtwMetadata.setLanguage(language);
        xtwMetadata.getRights().add(rights);

        // Act
        TransformOptions transformOptions = TransformOptions.fromXtwMetadata(xtwMetadata);

        // Assert
        Metadata metadata = transformOptions.getMetadata();
        assertEquals(metadata.getTitles().get(0), title);
        assertEquals(metadata.getAuthors().get(0).getLastname(), creator);
        assertEquals(metadata.getSubjects().get(0), subject);
        assertEquals(metadata.getDescriptions().get(0), description);
        assertEquals(metadata.getPublishers().get(0), publisher);
        assertEquals(metadata.getContributors().get(0).getLastname(), contributor);
        assertEquals(metadata.getDates().get(0).getValue(), date);
        assertEquals(metadata.getFormat(), format);
        assertEquals(metadata.getIdentifiers().get(0).getScheme(), identifier.getScheme());
        assertEquals(metadata.getIdentifiers().get(0).getValue(), identifier.getValue());
        assertEquals(metadata.getLanguage(), language);
        assertEquals(metadata.getRights().get(0), rights);
    }

}
