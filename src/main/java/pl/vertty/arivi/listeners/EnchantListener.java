
package pl.vertty.arivi.listeners;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.gui.enchant.EnchantGUI;
import cn.nukkit.level.Location;
import pl.vertty.arivi.utils.EnchantUtils;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.Listener;

public class EnchantListener implements Listener
{

    @EventHandler
    public void onRedstone(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if(b.getId() == Block.REDSTONE_BLOCK){
            Server.getInstance().getDefaultLevel().setBlock(b.getLocation(), Block.get(BlockID.AIR));
        }
    }


    @EventHandler
    public void onEnchant(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Config c = Main.getPlugin().getConfig();
        if (e.getBlock().getId() == 116 && e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                int books = 0;
                e.setCancelled(true);
                final Location XDDD = e.getBlock().getLocation();
                XDDD.setComponents((double)e.getBlock().getLocation().getFloorX(), (double)(e.getBlock().getLocation().getFloorY() + 1), (double)e.getBlock().getLocation().getFloorZ());
                for (final Location loc : EnchantUtils.getWalls(e.getBlock().getLocation(), 2)) {
                    if (loc.getLevelBlock().getId() == 47) {
                        ++books;
                    }
                }
                for (final Location loc : EnchantUtils.getWalls(XDDD, 2)) {
                    if (loc.getLevelBlock().getId() == 47) {
                        ++books;
                    }
                }
                if (e.getItem().getId() == 276 || e.getItem().getId() == 267 || e.getItem().getId() == 283 || e.getItem().getId() == 272 || e.getItem().getId() == 268) {
                    if (!c.getBoolean("enable.enchant.status")) {
                        e.setCancelled(true);
                        ChatUtil.sendTitle(p, "&9ENCAHNT", ChatUtil.fixColor("&cEnchanty na miecze i sety sa tymczasowo wylaczone!"));
                        return;
                    }
                    EnchantGUI.openMiecz(p, books);
                    e.setCancelled(true);
                }
                if (e.getItem().getId() == 302 || e.getItem().getId() == 310 || e.getItem().getId() == 311 || e.getItem().getId() == 312 || e.getItem().getId() == 313 || e.getItem().getId() == 314 || e.getItem().getId() == 306 || e.getItem().getId() == 298 || e.getItem().getId() == 303 || e.getItem().getId() == 315 || e.getItem().getId() == 307 || e.getItem().getId() == 299 || e.getItem().getId() == 304 || e.getItem().getId() == 316 || e.getItem().getId() == 308 || e.getItem().getId() == 300 || e.getItem().getId() == 305 || e.getItem().getId() == 317 || e.getItem().getId() == 309 || e.getItem().getId() == 301) {
                    if (!c.getBoolean("enable.enchant.status")) {
                        e.setCancelled(true);
                        ChatUtil.sendTitle(p, "&9ENCAHNT", ChatUtil.fixColor("&cEnchanty na miecze i sety sa tymczasowo wylaczone!"));
                        return;
                    }
                    EnchantGUI.openSety(p, books);
                    e.setCancelled(true);
                }
                if (e.getItem().getId() == 305 || e.getItem().getId() == 313 || e.getItem().getId() == 317 || e.getItem().getId() == 309 || e.getItem().getId() == 301) {
                    if (!c.getBoolean("enable.enchant.status")) {
                        e.setCancelled(true);
                        ChatUtil.sendTitle(p, "&9ENCAHNT", ChatUtil.fixColor("&cEnchanty na miecze i sety sa tymczasowo wylaczone!"));
                        return;
                    }
                    EnchantGUI.openButy(p, books);
                    e.setCancelled(true);
                }
                if (e.getItem().getId() == 277 || e.getItem().getId() == 284 || e.getItem().getId() == 256 || e.getItem().getId() == 273 || e.getItem().getId() == 269 || e.getItem().getId() == 278 || e.getItem().getId() == 285 || e.getItem().getId() == 257 || e.getItem().getId() == 274 || e.getItem().getId() == 270 || e.getItem().getId() == 279 || e.getItem().getId() == 286 || e.getItem().getId() == 258 || e.getItem().getId() == 275 || e.getItem().getId() == 271) {
                    for (final Enchantment e2 : e.getItem().getEnchantments()) {
                        if (e2.getId() == 15 && e2.getLevel() == 6) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                    e.setCancelled(true);
                    EnchantGUI.openKilof(p, books);
                }
            }
            else {
                ChatUtil.sendMessage((CommandSender)p, "&cMusisz miec item w rece!");
            }
        }
    }
}
