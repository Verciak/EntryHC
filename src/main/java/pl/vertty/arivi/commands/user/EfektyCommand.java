package pl.vertty.arivi.commands.user;

import cn.nukkit.Player;
import pl.vertty.arivi.commands.builder.PlayerCommand;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.gui.effects.EfektyGui;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.managers.UserManager;

public class EfektyCommand extends PlayerCommand
{
    public EfektyCommand() {
        super("efekty", "Wykup efekty", "/efekty", GroupType.PLAYER, new String[] { "" });
    }
    
    @Override
    public boolean onCommand(final Player p, final String[] args) {
        final User u = UserManager.getUser(p);
        EfektyGui.openSchowek(p);
        return true;
    }
}
