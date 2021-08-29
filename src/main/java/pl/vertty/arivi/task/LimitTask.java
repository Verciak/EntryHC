// 
// Decompiled by Procyon v0.5.36
// 

package pl.vertty.arivi.task;

import pl.vertty.arivi.Main;
import pl.vertty.arivi.guilds.data.User;
import java.util.Iterator;
import cn.nukkit.command.CommandSender;
import pl.vertty.arivi.utils.ChatUtil;
import cn.nukkit.item.Item;
import pl.vertty.arivi.guilds.managers.UserManager;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import cn.nukkit.scheduler.NukkitRunnable;

public class LimitTask extends NukkitRunnable
{
    public static Config c;
    
    public void run() {
        for (final Player p : Server.getInstance().getOnlinePlayers().values()) {
            final User u = UserManager.getUser(p);
            int koxSize = 0;
            int refSize = 0;
            int perSize = 0;
            for (final Item pinv : p.getInventory().getContents().values()) {
                if (pinv != null) {
                    if (pinv.getId() == 368) {
                        if (pinv.hasCustomName()) {
                            return;
                        }
                        perSize += pinv.getCount();
                    }
                    if (pinv.getId() == 466) {
                        if (pinv.hasCustomName()) {
                            return;
                        }
                        koxSize += pinv.getCount();
                    }
                    if (pinv.getId() != 322) {
                        continue;
                    }
                    if (pinv.hasCustomName()) {
                        return;
                    }
                    refSize += pinv.getCount();
                }
            }
            if (perSize > LimitTask.c.getInt("limit.perly")) {
                final int toRemove = perSize - LimitTask.c.getInt("limit.perly");
                u.addPerly(u.getPerly() + toRemove);
                p.getInventory().removeItem(new Item[] { new Item(368, Integer.valueOf(0), toRemove) });
                ChatUtil.sendMessage((CommandSender)p, "&7Miales przy sobie &e" + perSize + " &7perel");
                ChatUtil.sendMessage((CommandSender)p, "&7Limit to &e" + LimitTask.c.getInt("limit.perly") + " &7perel");
                ChatUtil.sendMessage((CommandSender)p, "&7Przedmioty zostaly przeniesione do schowka &e/schowek&7!");
            }
            if (koxSize > LimitTask.c.getInt("limit.kox")) {
                final int toRemove = koxSize - LimitTask.c.getInt("limit.kox");
                u.addKox(u.getKox() + toRemove);
                p.getInventory().removeItem(new Item[] { new Item(466, Integer.valueOf(0), toRemove) });
                ChatUtil.sendMessage((CommandSender)p, "&7Miales przy sobie &e" + koxSize + " &7koxow");
                ChatUtil.sendMessage((CommandSender)p, "&7Limit to &e" + LimitTask.c.getInt("limit.kox") + " &7koxow");
                ChatUtil.sendMessage((CommandSender)p, "&7Przedmioty zostaly przeniesione do schowka &e/schowek&7!");
            }
            if (refSize > LimitTask.c.getInt("limit.refy")) {
                final int toRemove = refSize - LimitTask.c.getInt("limit.refy");
                u.addRefy(u.getRefy() + toRemove);
                p.getInventory().removeItem(new Item[] { new Item(322, Integer.valueOf(0), toRemove) });
                ChatUtil.sendMessage((CommandSender)p, "&7Miales przy sobie &e" + refSize + " &7refili");
                ChatUtil.sendMessage((CommandSender)p, "&7Limit to &e" + LimitTask.c.getInt("limit.refy") + " &7refili");
                ChatUtil.sendMessage((CommandSender)p, "&7Przedmioty zostaly przeniesione do schowka &e/schowek&7!");
            }
        }
    }
    
    static {
        LimitTask.c = Main.getPlugin().getConfig();
    }
}
