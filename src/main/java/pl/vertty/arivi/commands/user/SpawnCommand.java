package pl.vertty.arivi.commands.user;

import cn.nukkit.Player;
import cn.nukkit.Server;
import pl.vertty.arivi.commands.builder.PlayerCommand;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.utils.TimerUtil;

public class SpawnCommand extends PlayerCommand
{
    public SpawnCommand() {
        super("spawn", "Teleportacja na spawn serwera", "/spawn", GroupType.PLAYER, new String[] { "" });
    }
    
    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length != 1) {
            TimerUtil.teleportSpawn(p, Server.getInstance().getDefaultLevel().getSpawnLocation().getLocation(), 10);
            return true;
        }
        final User u = UserManager.getUser(p);
        if (!u.can(GroupType.ADMIN)) {
            return ChatUtil.sendMessage(p, "&8>> &cNie masz dostepu!");
        }
        final Player o = Server.getInstance().getPlayer(args[0]);
        if (o == null) {
            return ChatUtil.sendMessage(p, "&cGracz jest offline!");
        }
        TimerUtil.teleportSpawn(o, Server.getInstance().getDefaultLevel().getSpawnLocation().getLocation(), 10);
        return ChatUtil.sendMessage(p, "&8>> &cPrzeteleportowales gracza &4" + o.getName() + " &cna spawn!");
    }
}
