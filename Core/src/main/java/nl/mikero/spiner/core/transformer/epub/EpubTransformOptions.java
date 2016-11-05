package nl.mikero.spiner.core.transformer.epub;

import nl.mikero.spiner.core.twine.extended.model.XtwMetadata;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Metadata;

/**
 * Collects all options available for a transformation.
 *
 * Currently it only contains all publication metadata as specified in the OPen Packaging Format specification section
 * <a href="http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.2">2.2: Publication Metadata</a>.
 *
 * @see <a href="http://www.idpf.org/epub/20/spec/OPF_2.0.1_draft.htm#Section2.2">Open Packaging Format (OPF) (2.2: Publication Metadata)</a>
 */
public final class EpubTransformOptions {

    public static final EpubTransformOptions EMPTY = new EpubTransformOptions(new Metadata());

    private final Metadata metadata;

    private EpubTransformOptions(Metadata metadata) {
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Constructs a new {@link EpubTransformOptions} from {@link XtwMetadata}.
     *
     * Most data maps directly from their {@link EpubTransformOptions} to their EPUB equivalent, but there are a couple of
     * deviations:
     *
     * <ul>
     *     <li>&lt;creator&gt; and &lt;contributor&gt; are considered to be the same</li>
     *     <li>&lt;source&gt; tags are ignored</li>
     *     <li>&lt;relation&gt; tags are ignored</li>
     *     <li>&lt;coverage&gt; tags are ignored</li>
     * </ul>
     *
     * @param xtwMetadata xml extended twine metadata object
     * @return a {@link EpubTransformOptions} contains all the metadata specified in {@link XtwMetadata}
     */
    public static EpubTransformOptions fromXtwMetadata(XtwMetadata xtwMetadata) {
        Metadata metadata = createMetadata(xtwMetadata);

        EpubTransformOptions options = new EpubTransformOptions(metadata);

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
