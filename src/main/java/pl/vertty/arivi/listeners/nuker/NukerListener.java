package pl.vertty.arivi.listeners.nuker;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.potion.Effect;
import cz.creeperface.nukkit.gac.player.PlayerAntyCheatNotify;
import pl.vertty.arivi.Cooldown;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.managers.ACManager;
import pl.vertty.arivi.objects.ACData;
import pl.vertty.arivi.utils.ChatUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NukerListener implements Listener {


    @EventHandler
    public void onNotify(final PlayerAntyCheatNotify e) {
        if (!Cooldown.getInstance().has(e.getPlayer(), "acnotify")) {
            Cooldown.getInstance().add(e.getPlayer(), "acnotify", 2.0f);
            for(Player p : Server.getInstance().getOnlinePlayers().values()){
                User u = UserManager.getUser(p);
                if(u.can(GroupType.HELPER)){
                    if(e.getReason().contains("movement check 7")){
                        ChatUtil.sendMessage(u.getPlayer(), "&9AC &8> &7Gracz &3" + e.getPlayer().getName() + " &7jest podejrzany o &4SPEED (50-100%) &8(&f"+p.getPing()+"ms&8)");
                    }else {
                        ChatUtil.sendMessage(u.getPlayer(), "&9AC &8> &7Gracz &3" + e.getPlayer().getName() + " &7jest podejrzany o &3" + e.getReason());
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            this.changes.put(e.getPlayer().getName(), System.currentTimeMillis());
        }
    }

    private final Map<String, Long> changes = new ConcurrentHashMap<String, Long>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreaks(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block i = e.getBlock();
        User u = UserManager.getUser(p);
        ACData ac = ACManager.getUser(e.getPlayer());

        if (p.getGamemode() == 0) {
            Item item = e.getItem();
            Enchantment eff = item.getEnchantment(Enchantment.ID_EFFICIENCY);
            if (!changes.containsKey(p.getName())) {
                if (!u.can(GroupType.ADMIN) && i.getLocation().getFloorX() <= 125 && i.getLocation().getFloorX() >= -125 && i.getLocation().getFloorZ() <= 125 && i.getLocation().getFloorZ() >= -125 && (i.getId() == 17 || i.getId() == 3 || i.getId() == 2) && (e.getPlayer().getInventory().getItemInHand().getId() == 279 || e.getPlayer().getInventory().getItemInHand().getId() == 271 || e.getPlayer().getInventory().getItemInHand().getId() == 286 || e.getPlayer().getInventory().getItemInHand().getId() == 286 || e.getPlayer().getInventory().getItemInHand().getId() == 258 || e.getPlayer().getInventory().getItemInHand().getId() == 275 || e.getPlayer().getInventory().getItemInHand().getId() == 277 || e.getPlayer().getInventory().getItemInHand().getId() == 269 || e.getPlayer().getInventory().getItemInHand().getId() == 284 || e.getPlayer().getInventory().getItemInHand().getId() == 284 || e.getPlayer().getInventory().getItemInHand().getId() == 256 || e.getPlayer().getInventory().getItemInHand().getId() == 273)) {
                    return;
                }
                if (ac.speedmineLimit(!(eff != null && eff.getLevel() >= 6 || (p.hasEffect(Effect.HASTE) && (p.getEffect(Effect.HASTE) != null && p.getEffect(Effect.HASTE).getAmplifier() >= 1))))) {
                    e.setCancelled();
                    for (Player paa : Server.getInstance().getOnlinePlayers().values()) {
                        User ua = UserManager.getUser(paa);
                        if (ua.can(GroupType.HELPER)) {
                            if (!Cooldown.getInstance().has(paa, "NUKER")) {
                                ChatUtil.sendMessage(paa, "&9AC &8> &7Gracz &4" + p.getName() + " &7posiada &4NUKERA &8(&4100%&8) | &8(&4" + p.getPing() + "ms&8)!");
                                Cooldown.getInstance().add(paa, "NUKER", 5f);
                            }
                        }
                    }
                } else {
                    for (Player paa : Server.getInstance().getOnlinePlayers().values()) {
                        User ua = UserManager.getUser(paa);
                        if (ua.can(GroupType.HELPER)) {
                            if (!Cooldown.getInstance().has(paa, "NUKER2")) {
                                ChatUtil.sendMessage(paa, "&9AC &8> &7Gracz &3" + p.getName() + " &7posiada &3NUKERA &8(&f10%&8) | &8(&f" + p.getPing() + "ms&8)!");
                                Cooldown.getInstance().add(paa, "NUKER2", 5f);
                            }
                        }
                    }
                }
                return;
            }
            changes.remove(p.getName());
        }
    }

}
