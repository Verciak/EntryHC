
package pl.vertty.arivi.commands.guild;

import cn.nukkit.Server;
import cn.nukkit.item.ItemID;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.gui.guild.GuildBoyFarmerGui;
import pl.vertty.arivi.gui.guild.GuildKopaczFarmerGui;
import pl.vertty.arivi.gui.guild.GuildSandFarmerGui;
import pl.vertty.arivi.managers.RoleManager;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.utils.guild.itemstack.ItemStackUtil;
import cn.nukkit.plugin.Plugin;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.task.RegenerationTask;
import pl.vertty.arivi.utils.guild.TimeUtil;
import pl.vertty.arivi.utils.guild.DataUtil;

import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import cn.nukkit.item.Item;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.objects.guild.Guild;
import pl.vertty.arivi.utils.guild.ChatUtil;
import pl.vertty.arivi.objects.yml.Config;
import pl.vertty.arivi.managers.guild.GuildManager;
import cn.nukkit.Player;
import pl.vertty.arivi.utils.guild.command.PlayerCommand;

public class GuildPanelCommand extends PlayerCommand
{
    @Override
    public boolean onCommand(final Player player, final String[] array) {
        final Guild guild = GuildManager.getGuild(player);
        if (guild == null) {
            player.sendMessage(ChatUtil.fixColor(Config.GUILD_NOT_GUILD));
            return false;
        }
//        GuildPanelGui.openInv(player);
        return false;
    }

    
    public GuildPanelCommand() {
        super("panel", "/g panel", GroupType.PLAYER);
    }
}
