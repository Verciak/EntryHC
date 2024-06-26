package pl.vertty.arivi.commands.admin;

import pl.vertty.arivi.utils.ChatUtil;
import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.commands.builder.Command;

public class SetSpawnCommand extends Command
{
    public SetSpawnCommand() {
        super("setspawn", "Ustawianie spawna", "/setspawn", GroupType.ADMIN, new String[] { "" });
    }
    
    @Override
    public boolean onExecute(final CommandSender sender, final String[] args) {
        final Player p = (Player)sender;
        Server.getInstance().getDefaultLevel().setSpawnLocation(p.getLocation());
        return ChatUtil.sendMessage(p, "&8>> &cUstawiles spawn!");
    }
}
