package nl.mikero.turntopassage.core.service;

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
}
