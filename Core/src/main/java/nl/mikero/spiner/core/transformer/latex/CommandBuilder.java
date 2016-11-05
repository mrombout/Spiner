package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

public class CommandBuilder<T extends AbstractCommand> {
    public static <T extends AbstractCommand> T create(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}
