package pl.vertty.arivi.commands.admin;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import org.apache.commons.lang3.StringUtils;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.commands.builder.Command;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.utils.ChatUtil;

import java.util.List;

public class WhiteListCommand extends Command {
    public WhiteListCommand() {
        super("whitelist", "whitelist serwera", "/whitelist <on|off|add|remove|list> [gracz]", GroupType.ADMIN, new String[]{"wl"});
    }

    public static Config c = Main.getPlugin().getConfig();
    @Override
    public boolean onExecute(final CommandSender sender, final String[] args) {
        if (args.length < 1) {
            return ChatUtil.sendMessage(sender, "/wl <add|remove|list|reason|on|off>");
        }
        final String var3 = args[0];
        byte var4 = -1;
        switch (var3.hashCode()) {
            case -934964668: {
                if (var3.equals("reason")) {
                    var4 = 10;
                    break;
                }
                break;
            }
            case -934610812: {
                if (var3.equals("remove")) {
                    var4 = 7;
                    break;
                }
                break;
            }
            case -773038450: {
                if (var3.equals("wylacz")) {
                    var4 = 2;
                    break;
                }
                break;
            }
            case 3551: {
                if (var3.equals("on")) {
                    var4 = 1;
                    break;
                }
                break;
            }
            case 96417: {
                if (var3.equals("add")) {
                    var4 = 5;
                    break;
                }
                break;
            }
            case 109935: {
                if (var3.equals("off")) {
                    var4 = 3;
                    break;
                }
                break;
            }
            case 3322014: {
                if (var3.equals("list")) {
                    var4 = 8;
                    break;
                }
                break;
            }
            case 3599799: {
                if (var3.equals("usun")) {
                    var4 = 6;
                    break;
                }
                break;
            }
            case 95758114: {
                if (var3.equals("dodaj")) {
                    var4 = 4;
                    break;
                }
                break;
            }
            case 106859053: {
                if (var3.equals("powod")) {
                    var4 = 9;
                    break;
                }
                break;
            }
            case 113212835: {
                if (var3.equals("wlacz")) {
                    var4 = 0;
                    break;
                }
                break;
            }
        }
        switch (var4) {
            case 0:
            case 1: {
                if (c.getBoolean("wl.enable")) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest juz on!");
                }
                c.set("wl.enable", true);
                c.save();
                return ChatUtil.sendMessage(sender, "&8>> &cWhitelist zostala wlaczona!");
            }
            case 2:
            case 3: {
                if (!c.getBoolean("wl.enable")) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &cWhitelist jest off!");
                }
                c.set("wl.enable", false);
                c.save();
                return ChatUtil.sendMessage(sender, "&8>> &cWhitelist zostala wylaczona!");
            }
            case 4:
            case 5: {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(sender, "/wl add <gracz>");
                }
                final String nick = args[1];
                if (c.getStringList("wl.list").contains(nick)) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " jest juz na whitelist!");
                }
                List<String> list = c.getStringList("wl.list");
                list.add(nick);
                c.set("wl.list", list);
                c.save();
                return ChatUtil.sendMessage(sender, "&cGracz &6" + nick + " &czostal dodany do whitelist!");
            }
            case 6:
            case 7: {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(sender, "/wl remove <gracz>");
                }
                final String nick = args[1];
                if (!c.getStringList("wl.list").contains(nick)) {
                    return ChatUtil.sendMessage(sender, "&4Blad: &c" + nick + " nie jest na whitelist!");
                }
                c.getStringList("wl.list").remove(nick);
                c.save();
                return ChatUtil.sendMessage(sender, "&cGracz &6" + nick + " &czostal usuniety z whitelist!");
            }
            case 8: {
                return ChatUtil.sendMessage(sender, "&8>> &cLista graczy na whitelist: &6" + StringUtils.join(c.getStringList("wl.list"), "&8, &6"));
            }
            case 9:
            case 10: {
                if (args.length < 2) {
                    return ChatUtil.sendMessage(sender, "/wl reason <powod>");
                }
                final String nick = StringUtils.join((Object[])args, " ", 1, args.length);
                c.set("wl.reason", ChatUtil.fixColor(nick));
                c.save();
                return ChatUtil.sendMessage(sender, "&8>> &cUstawiles powod whitelist na: &r" + nick);
            }
            default: {
                return ChatUtil.sendMessage(sender, "/wl <add|remove|list|reason|on|off>");
            }
        }
    }
}