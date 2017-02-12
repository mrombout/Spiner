package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.BasicCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.UsePackageCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.WhitelineCommand;

/**
 * A {@link LatexDocument} that is configured to create a gamebook.
 */
public class GamebookLatexDocument extends LatexDocument {
    /**
     * Constructs a new GamebookLatexDocument.
     */
    public GamebookLatexDocument() {
        super();
        addCommand(new BasicCommand("documentclass")
                .options()
                    .add("b5paper")
                    .build()
                .parameters()
                    .add("article")
                    .build());
        addCommand(new UsePackageCommand().options().add("utf8").build().parameters().add("inputenc").build());
        addCommand(new UsePackageCommand().parameters().add("wallpaper").build());
        addCommand(new UsePackageCommand().parameters().add("gamebook").build());
        addCommand(new WhitelineCommand());
    }
}
