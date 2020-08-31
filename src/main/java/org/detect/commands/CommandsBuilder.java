package org.detect.commands;

import org.jetbrains.annotations.NotNull;


public class CommandsBuilder {
    private static CommandsChecker commandsChecker;

    public static void setStaticCommandsChecker(@NotNull CommandsChecker commandsChecker) {
        CommandsBuilder.commandsChecker = commandsChecker;
    }

    public static void clearStaticCommandsChecker() {
        CommandsBuilder.commandsChecker = null;
    }

    private static CommandsChecker staticInstance() {
        if (commandsChecker == null) {
            throw new IllegalStateException("The static API can only be used within a commands() call.");
        }
        return commandsChecker;
    }

    public static void command(@NotNull String command, @NotNull ActionHandler action) {
        staticInstance().command(command, action);
    }
}
