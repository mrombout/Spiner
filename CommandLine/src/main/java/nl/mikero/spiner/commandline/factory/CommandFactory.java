package nl.mikero.spiner.commandline.factory;

import com.beust.jcommander.JCommander;
import com.google.inject.assistedinject.Assisted;
import nl.mikero.spiner.commandline.command.*;

public interface CommandFactory {
    MainCommand createMainCommand();

    HelpCommand createHelpCommand(JCommander jCommander);
    VersionCommand createVersionCommand();

    TransformCommand createTransformCommand();
}
