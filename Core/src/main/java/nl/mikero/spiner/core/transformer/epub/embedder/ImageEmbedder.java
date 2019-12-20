package nl.mikero.spiner.core.transformer.epub.embedder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Objects;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.service.MediatypeService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * {@inheritDoc}
 *
 * Images are all places in the {@code /Images/} folder. Each image is renamed
 * to the SHA-2 hash of theirs contents.
 *
 * This means that different images that originally had the same name can
 * exist. While images that have different names but the same content will only
 * be saved once.
 */
public class ImageEmbedder implements Embedder {

    private final MessageDigest messageDigest;

    /**
     * Constructs a new ImageEmbedder.
     *
     * @param messageDigest the message digest used to create a file name
     */
    public ImageEmbedder(final MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    /**
     * {@inheritDoc}
     *
     * Images will be read and their hashed content will be the new name of the
     * embedded file. The extension of the file is preserved.
     *
     * @param url url of the image as defined by the user in twine
     */
    @Override
    public final String getHref(final URL url) throws IOException {
        String hash = DigestUtils.sha256Hex(url.openStream());
        return getFileName(hash, url);
    }

    /**
     * Embed the file at the given url into the EPUB.
     *
     * To embed the image resource the contents of the file is read. Then a hash is generated from the file contents to
     * create a unique file name. Then the content type of the file is determined by using
     * {@link MediatypeService#determineMediaType(String)}. And finally the resources is added to the {@link Book}.
     *
     * @param book book to embed resource in
     * @param url url to resource that should be embedded
     * @throws IOException when file contents can not be read
     */
    @Override
    public final void embed(final Book book, final URL url) throws IOException {
        Objects.requireNonNull(book);
        Objects.requireNonNull(url);

        // read and hash file
        byte[] fileContent = new byte[0];
        try(InputStream in = url.openStream()) {
            DigestInputStream dis = new DigestInputStream(in, messageDigest);
            fileContent = IOUtils.toByteArray(dis);
        }

        // create resource
        String fileName = getFileName(new String(Hex.encodeHex(messageDigest.digest())), url);
        Resource imgResource = new Resource(
                null,
                fileContent,
                fileName,
                MediatypeService.determineMediaType(FilenameUtils.getName(url.toString())));

        book.addResource(imgResource);

        messageDigest.reset();
    }

    /**
     * Returns the filename for a given hash and URL.
     *
     * @param hash hash of the file
     * @param url url of the file
     * @return filename to store/embed the file at
     */
    private String getFileName(final String hash, final URL url) {
        String originalFileName = FilenameUtils.getName(url.toString());
        String originalExtension = FilenameUtils.getExtension(originalFileName);

        return String.format("Images/%s.%s", hash, originalExtension) ;
    }
}
