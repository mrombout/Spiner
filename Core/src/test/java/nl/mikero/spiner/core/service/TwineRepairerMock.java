package nl.mikero.spiner.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import nl.mikero.spiner.core.twine.TwineRepairer;
import org.apache.commons.io.IOUtils;

public class TwineRepairerMock implements TwineRepairer {
    private final InputStream inputStream;

    public TwineRepairerMock(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void repair(final InputStream inputStream, final OutputStream outputStream) {
        try {
            IOUtils.copy(this.inputStream, outputStream);
        } catch (IOException e) {
            throw new TwineRepairFailedException("Could not repair input stream", e);
        }
    }
}
