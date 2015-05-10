package nl.mikero.turntopassage.core.embedder;

import nl.mikero.turntopassage.core.model.TwPassagedata;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.service.MediatypeService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
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
    public String getHref(String url) throws IOException {
        String hash = DigestUtils.sha256Hex(IOUtils.toByteArray(Files.newInputStream(Paths.get(url))));
        return getFileName(hash, Paths.get(url));
    }

    /**
     * Embeds an image resource inside the EPUB archive.
     *
     * @param book book to embed the image in
     * @param url url to the image
     * @throws IOException when an I/O errors occurs with reading the input file
     */
    @Override
    public void embed(Book book, String url) throws IOException {
        embed(book, Paths.get(url));
    }

    public void embed(Book book, Path path) throws IOException {
        Objects.requireNonNull(book);
        Objects.requireNonNull(path);

        // read and hash file
        byte[] fileContent = new byte[0];
        try(InputStream in = Files.newInputStream(path)) {
            DigestInputStream dis = new DigestInputStream(in, messageDigest);
            fileContent = IOUtils.toByteArray(dis);
        }

        // create resource
        String fileName = getFileName(new String(Hex.encodeHex(messageDigest.digest())), path.getFileName());
        Resource imgResource = new Resource(null, fileContent, fileName, MediatypeService.determineMediaType(path.getFileName().toString()));

        book.addResource(imgResource);

        messageDigest.reset();
    }

    private String getFileName(String hash, Path originalPath) {
        System.out.println(hash);
        String originalFileName = originalPath.getFileName().toString();
        String originalExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        return "Images/" + hash + originalExtension;
    }
}
