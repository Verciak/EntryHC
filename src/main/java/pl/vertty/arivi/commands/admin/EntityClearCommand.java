package pl.vertty.arivi.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.utils.TextFormat;
import pl.vertty.arivi.commands.builder.Command;
import pl.vertty.arivi.enums.GroupType;

public class EntityClearCommand extends Command
{
    public EntityClearCommand() {
        super("entity", "Usuwanie entity serwera", "/entity", GroupType.ADMIN, new String[] { "" });
    }
    
    @Override
    public boolean onExecute(final CommandSender sender, final String[] args) {
        int amount = 0;
        final Player player = (Player)sender;
        for (final Entity entity : player.getLevel().getEntities()) {
            if (!(entity instanceof Player)) {
                entity.getLevel().removeEntity(entity);
                ++amount;
            }
        }
        player.sendMessage(TextFormat.DARK_GREEN + "Wypierdalam tych smieci..." + TextFormat.RED + " " + amount);
        return false;
    }
}
