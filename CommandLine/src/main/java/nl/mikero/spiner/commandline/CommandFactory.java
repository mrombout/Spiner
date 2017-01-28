package nl.mikero.spiner.commandline;

import com.beust.jcommander.JCommander;
import nl.mikero.spiner.commandline.command.*;

public interface CommandFactory {
    HelpCommand createHelpCommand(JCommander jCommander);
    VersionCommand createVersionCommand();

    TransformCommand createTransformCommand();
}
