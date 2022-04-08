
package pl.vertty.arivi.listeners.connection;

import cn.nukkit.Server;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.ServerStopEvent;
import pl.vertty.arivi.DiscordWebhook;
import pl.vertty.arivi.Main;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.EventHandler;
import pl.vertty.arivi.objects.Ban;
import cn.nukkit.Player;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.utils.DataUtil;
import pl.vertty.arivi.managers.BanManager;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.event.Listener;

import java.awt.*;
import java.io.IOException;

public class CheckLoginListener implements Listener
{
    public static Config c = Main.getPlugin().getConfig();


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage("");
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/961340379188330516/lmZKM1OdgAyHIx_keQHFiuvNveYek1lyZcmGSHsTtR24AcZaYdq1BjgsFhQO0_la5yMQ");
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
        embedObject.setAuthor("LOGI JOIN", "", "http://cravatar.eu/avatar/"+ e.getPlayer().getName() +"/64.png");
        embedObject.setColor(new Color(0x00FF00));
        embedObject.setDescription("Gracz **" + e.getPlayer().getName() + "** dolaczyl serwer!");
        embedObject.setTitle("");
        webhook.addEmbed(embedObject);
        try {
            webhook.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage("");
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/961340379188330516/lmZKM1OdgAyHIx_keQHFiuvNveYek1lyZcmGSHsTtR24AcZaYdq1BjgsFhQO0_la5yMQ");
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
        embedObject.setAuthor("LOGI LEFT", "", "http://cravatar.eu/avatar/"+ e.getPlayer().getName() +"/64.png");
        embedObject.setColor(new Color(0x00FF00));
        embedObject.setDescription("Gracz **" + e.getPlayer().getName() + "** opuscil serwer!");
        embedObject.setTitle("");
        webhook.addEmbed(embedObject);
        try {
            webhook.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onBreak(final ServerStopEvent e) {
        for(Player player : Server.getInstance().getOnlinePlayers().values())
        player.close(player.getLeaveMessage(), ChatUtil.fixColor("&cSerwer jest restartowany!"));

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLogin(final PlayerLoginEvent e) {
        final Player p = e.getPlayer();
        final Ban ban = BanManager.getBan(p);
        if (c.getBoolean("wl.enable") && !c.getStringList("wl.list").contains(p.getName())) {
            e.setKickMessage("\n" + ChatUtil.fixColor(c.getString("wl.reason")).replace("{N}", "\n"));
            e.setCancelled(true);
        }
        if (ban != null) {
            if (ban.getTime() != 0L && ban.getTime() <= System.currentTimeMillis()) {
                BanManager.unban(ban);
                return;
            }
            final String reason = "\n&4Zostales zbanowany przez &c" + ban.getAdmin() + "\n&4Powod: &c" + ban.getReason() + "\n&4Wygasa: &c" + ((ban.getTime() == 0L) ? "Nigdy!" : ("&4za &c" + DataUtil.secondsToString(ban.getTime())));
            e.setKickMessage(ChatUtil.fixColor(reason));
            e.setCancelled(true);
        }
    }

}
