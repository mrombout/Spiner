package nl.mikero.turntopassage.core.service;

import nl.mikero.turntopassage.core.TwineRepairer;
import nl.mikero.turntopassage.core.exception.TwineRepairFailedException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TwineRepairerMock implements TwineRepairer {
    private final InputStream inputStream;

    public TwineRepairerMock(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void repair(InputStream inputStream, OutputStream outputStream) throws TwineRepairFailedException {
        try {
            IOUtils.copy(this.inputStream, outputStream);
        } catch (IOException e) {
            throw new TwineRepairFailedException("Could not repair input stream", e);
        }
    }
}
