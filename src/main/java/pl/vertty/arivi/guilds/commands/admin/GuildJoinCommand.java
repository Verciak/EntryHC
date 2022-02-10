// 
// Decompiled by Procyon v0.5.36
// 

package pl.vertty.arivi.guilds.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.guilds.data.guild.Guild;
import pl.vertty.arivi.guilds.data.yml.Config;
import pl.vertty.arivi.guilds.managers.guild.GuildManager;
import pl.vertty.arivi.guilds.managers.guild.NameTagManager;
import pl.vertty.arivi.guilds.utils.ChatUtil;
import pl.vertty.arivi.guilds.utils.command.PlayerCommand;

public class GuildJoinCommand extends PlayerCommand
{
    public GuildJoinCommand() {
        super("dolacz", "/ga dolacz <tag> <nick>", GroupType.ADMIN, new String[0]);
    }
    
    @Override
    public boolean onCommand(final Player player, final String[] array) {
        if (array.length < 2) {
            player.sendMessage(ChatUtil.fixColor(Config.COMMAND_USAGE.replace("{USAGE}", this.getUsage())));
            return false;
        }
        final Guild guild = GuildManager.getGuild(array[1]);
        if (guild == null) {
            player.sendMessage(ChatUtil.fixColor(Config.GUILD_NOT_EXISTS));
            return false;
        }
        final Player player2 = Server.getInstance().getPlayer(array[2]);
        if (player2 == null) {
            player.sendMessage(ChatUtil.fixColor(Config.USER_NULL));
            return false;
        }
        if (GuildManager.getGuild(player2) != null) {
            player.sendMessage(ChatUtil.fixColor(Config.GUILD_IS_GUILD2));
            return false;
        }
        if (guild.getInvites().contains(player2)) {
            guild.getInvites().remove(player2);
        }
        guild.addMember(player2);
        NameTagManager.refresh();
        Server.getInstance().broadcastMessage(ChatUtil.fixColor(Config.GUILD_COMMAND_JOIN_MESSAGE.replace("{NICK}", player2.getName()).replace("{TAG}", guild.getTag())));
        return false;
    }
}
