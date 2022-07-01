
package pl.vertty.arivi.utils.guild.command;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandMap;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.plugin.PluginManager;

import java.util.HashMap;

public class CommandManager
{
    private static CommandMap cmdMap;

    public static void register(final ConsoleCommand consoleCommand) {
        if (CommandManager.cmdMap == null) {
            CommandManager.cmdMap = Server.getInstance().getCommandMap();
        }
        CommandManager.cmdMap.register(consoleCommand.getName(), (Command)consoleCommand);
    }
    
    static {
        CommandManager.cmdMap = Server.getInstance().getCommandMap();
    }
}
