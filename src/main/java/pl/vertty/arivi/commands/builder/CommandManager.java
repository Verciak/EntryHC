package pl.vertty.arivi.commands.builder;

import cn.nukkit.plugin.PluginManager;
import java.util.Arrays;
import pl.vertty.arivi.listeners.command.UnknownCommandListener;
import cn.nukkit.Server;
import cn.nukkit.command.CommandMap;
import cn.nukkit.command.SimpleCommandMap;
import pl.vertty.arivi.utils.Reflection;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommandManager
{
    public static final HashMap<String, Command> commands;
    private static final Reflection.FieldAccessor<SimpleCommandMap> f;
    private static CommandMap cmdMap;
    
    public static void register(final Command cmd) {
        if (CommandManager.cmdMap == null) {
            CommandManager.cmdMap = CommandManager.f.get(Server.getInstance().getPluginManager());
        }
        CommandManager.cmdMap.register(cmd.getName(), cmd);
        CommandManager.commands.put(cmd.getName(), cmd);
        UnknownCommandListener.registeredCommands.add(cmd.getName());
        if (cmd.getAliases() != null) {
            UnknownCommandListener.registeredCommands.addAll(Collections.singleton(Arrays.toString(cmd.getAliases())));
        }
    }
    
    static {
        commands = new HashMap<>();
        f = Reflection.getField(PluginManager.class, "commandMap", SimpleCommandMap.class);
        CommandManager.cmdMap = CommandManager.f.get(Server.getInstance().getPluginManager());
    }
}
