package nl.mikero.turntopassage.core.embedder;

import nl.mikero.turntopassage.core.model.TwPassagedata;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.service.MediatypeService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

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

    public ImageEmbedder(MessageDigest messageDigest) {
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
    public String getHref(URL url) throws IOException {
        String hash = DigestUtils.sha256Hex(url.openStream());
        return getFileName(hash, url);
    }

    public void embed(Book book, URL url) throws IOException {
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
        Resource imgResource = new Resource(null, fileContent, fileName, MediatypeService.determineMediaType(FilenameUtils.getName(url.toString())));

        book.addResource(imgResource);

        messageDigest.reset();
    }

    private String getFileName(String hash, URL url) {
        String originalFileName = FilenameUtils.getName(url.toString());
        String originalExtension = FilenameUtils.getExtension(originalFileName);

        return "Images/" + hash + originalExtension;
    }
}
