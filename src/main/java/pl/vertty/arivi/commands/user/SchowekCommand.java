// 
// Decompiled by Procyon v0.5.36
// 

package pl.vertty.arivi.commands.user;

import pl.vertty.arivi.gui.SchowekGui;
import cn.nukkit.Player;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.commands.builder.PlayerCommand;

public class SchowekCommand extends PlayerCommand
{
    public SchowekCommand() {
        super("schowek", "Dobierz sie do limitu", "/schowek", GroupType.PLAYER, new String[] { "" });
    }
    
    @Override
    public boolean onCommand(final Player p, final String[] args) {
        SchowekGui.openSchowek(p);
        return true;
    }
}
