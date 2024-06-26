package pl.vertty.arivi.commands.moderator;

import cn.nukkit.utils.Config;
import org.apache.commons.lang3.StringUtils;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.commands.builder.Command;

public class AutoMessageCommand extends Command
{
    public AutoMessageCommand() {
        super("amsg", "Automatyczne wiadomosci", "/amsg <add/remove/list>", GroupType.MODERATOR, new String[] { "automsg" });
    }
    
    @Override
    public boolean onExecute(final CommandSender sender, final String[] args) {
        final Player p = (Player)sender;
        final Config c = Main.getPlugin().getConfig();
        if (args.length < 1) {
            return ChatUtil.sendMessage(p, this.getUsage());
        }
        final String s = args[0];
        switch (s) {
            case "add": {
                final String msg = StringUtils.join(args, " ", 1, args.length);
                c.getStringList("automsg").add(msg);
                return ChatUtil.sendMessage(p, "&8>> &7Dodales do auto msg &6" + msg);
            }
            case "remove": {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(p, "/automsg remove <id>");
                }
                if (!ChatUtil.isInteger(args[1])) {
                    return ChatUtil.sendMessage(p, "&4Blad: &cTo nie jest id");
                }
                if (c.getStringList("automsg").size() == 0) {
                    return ChatUtil.sendMessage(p, "&8>> &cBrak wiadomosci!");
                }
                final int i = Integer.parseInt(args[1]);
                if (c.getStringList("automsg").size() <= i) {
                    return ChatUtil.sendMessage(p, "&4Blad: &cZle id!");
                }
                ChatUtil.sendMessage(p, "&8>> &7Usunales automsg &c" + c.getStringList("automsg").get(i));
                c.getStringList("automsg").remove(i);
                return true;
            }
            case "list": {
                if (c.getStringList("automsg").size() == 0) {
                    return ChatUtil.sendMessage(p, "&8>> &cBrak wiadomosci!");
                }
                int id = 0;
                ChatUtil.sendMessage(p, "&8>> &6AutoMSG -> \n");
                for (final String s6 : c.getStringList("automsg")) {
                    ChatUtil.sendMessage(p, " &8(" + id + "&8) &r" + s6 + "\n");
                    ++id;
                }
                return true;
            }
            default: {
                return ChatUtil.sendMessage(p, this.getUsage());
            }
        }
    }
}
