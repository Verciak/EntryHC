package pl.vertty.arivi.commands.user;

import cn.nukkit.Player;
import pl.vertty.arivi.commands.builder.PlayerCommand;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.gui.home.DelHomeInventory;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.managers.UserManager;

public class DelHomeCommand extends PlayerCommand
{
    public DelHomeCommand() {
        super("delhome", "Usuwanie domu", "/delhome", GroupType.PLAYER, new String[] { "" });
    }
    
    @Override
    public boolean onCommand(final Player p, final String[] args) {
        final User u = UserManager.getUser(p);
        if (u == null) {
            return true;
        }
        DelHomeInventory.openDelHome(p);
        return true;
    }
}
