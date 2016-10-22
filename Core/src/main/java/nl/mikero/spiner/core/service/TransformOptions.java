package nl.mikero.spiner.core.service;

import nl.mikero.spiner.core.model.XtwMetadata;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Metadata;

public final class TransformOptions {

    public static final TransformOptions EMPTY = new TransformOptions(new Metadata());

    private final Metadata metadata;

    public TransformOptions(Metadata metadata) {
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public static TransformOptions fromXtwMetadata(XtwMetadata xtwMetadata) {
        Metadata metadata = createMetadata(xtwMetadata);

        TransformOptions options = new TransformOptions(metadata);

        return options;
    }

    private static Metadata createMetadata(XtwMetadata xtwMetadata) {
        Metadata metadata = new Metadata();

        // title
        metadata.setTitles(xtwMetadata.getTitle());

        // language
        metadata.setLanguage(xtwMetadata.getLanguage());

        // identifier
        for (XtwMetadata.Identifier identifier : xtwMetadata.getIdentifier()) {
            nl.siegmann.epublib.domain.Identifier epubIdentifier = new nl.siegmann.epublib.domain.Identifier(identifier.getScheme(), identifier.getValue());
            metadata.addIdentifier(epubIdentifier);
        }

        // creator
        for(String creator : xtwMetadata.getCreator()) {
            Author author = new Author(creator);
            metadata.addAuthor(author);
        }

        // contributor
        for(String contributor : xtwMetadata.getContributor()) {
            Author author = new Author(contributor);
            metadata.addContributor(author);
        }

        // publisher
        metadata.setPublishers(xtwMetadata.getPublisher());

        // subject
        metadata.setSubjects(xtwMetadata.getSubject());

        // description
        metadata.setDescriptions(xtwMetadata.getDescription());

        // date
        for(String dateString : xtwMetadata.getDate()) {
            Date date = new Date(dateString);
            metadata.addDate(date);
        }

        // type
        metadata.setTypes(xtwMetadata.getType());

        // format
        metadata.setFormat(xtwMetadata.getFormat());

        // rights
        metadata.setRights(xtwMetadata.getRights());

        return metadata;
    }
}
