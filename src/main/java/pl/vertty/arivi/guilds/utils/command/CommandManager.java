// 
// Decompiled by Procyon v0.5.36
// 

package pl.vertty.arivi.guilds.utils.command;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandMap;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.plugin.PluginManager;
import pl.vertty.arivi.utils.Reflection;

import java.util.HashMap;

public class CommandManager
{
    private static CommandMap cmdMap;
    public static final HashMap<String, Command> commands;
    private static final Reflection.FieldAccessor<SimpleCommandMap> f;
    
    public static void register(final ConsoleCommand consoleCommand) {
        if (CommandManager.cmdMap == null) {
            CommandManager.cmdMap = (CommandMap)CommandManager.f.get(Server.getInstance().getPluginManager());
        }
        CommandManager.cmdMap.register(consoleCommand.getName(), (Command)consoleCommand);
        CommandManager.commands.put(consoleCommand.getName(), consoleCommand);
    }
    
    static {
        commands = new HashMap<String, Command>();
        f = Reflection.getField(PluginManager.class, "commandMap", SimpleCommandMap.class);
        CommandManager.cmdMap = (CommandMap)CommandManager.f.get(Server.getInstance().getPluginManager());
    }
}
