package pl.vertty.arivi.commands.builder;

import java.util.Arrays;
import pl.vertty.arivi.listeners.command.UnknownCommandListener;
import cn.nukkit.Server;
import cn.nukkit.command.CommandMap;

import java.util.Collections;

public class CommandManager
{
    private static CommandMap cmdMap;
    
    public static void register(final Command cmd) {
        if (CommandManager.cmdMap == null) {
            CommandManager.cmdMap = Server.getInstance().getCommandMap();
        }
        CommandManager.cmdMap.register(cmd.getName(), cmd);
        UnknownCommandListener.registeredCommands.add(cmd.getName());
        if (cmd.getAliases() != null) {
            UnknownCommandListener.registeredCommands.addAll(Collections.singleton(Arrays.toString(cmd.getAliases())));
        }
    }
}
