package org.detect.commands;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CommandsChecker {
    private static final Logger log = LoggerFactory.getLogger(CommandsChecker.class);
    private final ConcurrentHashMap<String, ActionHandler> commands = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<OptionHandler>> cmdOpts = new ConcurrentHashMap<>();

    public CommandsChecker commands(@NotNull CommandsGroup commandsGroup) {
        CommandsBuilder.setStaticCommandsChecker(this);
        commandsGroup.addCommands();
        CommandsBuilder.clearStaticCommandsChecker();
        return this;
    }

    //TODO подумать как проапгрейдить опции
    public CommandsChecker command(@NotNull String command, @NotNull ActionHandler handler, OptionHandler... options) {
        if (null != commands.putIfAbsent(command, handler)) {
            log.error("This command '{}' already is exist.", command);
        } else {
            final Set<OptionHandler> optionHandlers = cmdOpts.computeIfAbsent(command, opt -> ConcurrentHashMap.newKeySet());
            optionHandlers.addAll(Arrays.asList(options));
        }
        return this;
    }

    public void checkCommand(final String line) {
        final String[] lineCommands = line.split(" ");
        final String command = lineCommands[0];

        final ActionHandler actionHandler = commands.get(command);
        if (actionHandler != null) {
            if (lineCommands.length > 1) {
                for (int i = 1; i < lineCommands.length; i++) {
                    if (!lineCommands[i].startsWith("--")) {
                        actionHandler.handle(lineCommands[i]);
                    }
                }
            } else {
                actionHandler.handle(null);
            }
        } else {
            log.error("This command '{}' is incorrect", command);
        }
    }
}
