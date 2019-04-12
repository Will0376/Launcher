package ru.gravit.launchserver.command.basic;

import ru.gravit.launchserver.LaunchServer;
import ru.gravit.utils.command.Command;
import ru.gravit.utils.command.CommandCategory;
import ru.gravit.utils.command.CommandException;
import ru.gravit.utils.command.CommandHandler;
import ru.gravit.utils.helper.LogHelper;

import java.util.Map;
import java.util.Map.Entry;

public final class HelpCommand extends ru.gravit.launchserver.command.Command {
    private static void printCommand(String name, Command command) {
        String args = command.getArgsDescription();
        LogHelper.subInfo("%s %s - %s", name, args == null ? "[nothing]" : args, command.getUsageDescription());
    }

    private static void printCategory(String name, String description)
    {
        if(description != null) LogHelper.info("Category: %s - %s", name, description);
        else LogHelper.info("Category: %s", name);
    }

    public HelpCommand(LaunchServer server) {
        super(server);
    }

    @Override
    public String getArgsDescription() {
        return "[command name]";
    }

    @Override
    public String getUsageDescription() {
        return "Print command usage";
    }

    @Override
    public void invoke(String... args) throws CommandException {
        if (args.length < 1) {
            printCommands();
            return;
        }

        // Print command help
        printCommand(args[0]);
    }

    private void printCommand(String name) throws CommandException {
        printCommand(name, server.commandHandler.lookup(name));
    }

    private void printCommands() {
        for(CommandHandler.Category category : server.commandHandler.getCategories())
        {
            printCategory(category.name, category.description);
            for (Entry<String, Command> entry : category.category.commandsMap().entrySet())
                printCommand(entry.getKey(), entry.getValue());
        }
        printCategory("Base", null);
        for (Entry<String, Command> entry : server.commandHandler.getBaseCategory().commandsMap().entrySet())
            printCommand(entry.getKey(), entry.getValue());

    }
}
