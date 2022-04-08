package pl.vertty.arivi.listeners.worldedit;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.worldedit.PlayerData;

public class WorldEditListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (Main.isSelector(p) && p.getInventory().getItemInHand().getId() == Item.WOODEN_AXE) {
            PlayerData data = Main.getPlayerData(p);

            data.getSelection().pos2 = b.getLocation().clone();
            p.sendMessage(TextFormat.GREEN + "Pierwsza pozycja zaznaczona na " + TextFormat.BLUE + b.x + TextFormat.GREEN + ", " + TextFormat.BLUE + b.y + TextFormat.GREEN + ", " + TextFormat.BLUE + b.z + TextFormat.GREEN);
            e.setCancelled();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (Main.isSelector(p) && p.getInventory().getItemInHand().getId() == Item.WOODEN_AXE) {
            PlayerData data = Main.getPlayerData(p);

            switch (e.getAction()) {
                case RIGHT_CLICK_BLOCK:
                    data.getSelection().pos1 = b.getLocation().clone();
                    p.sendMessage(TextFormat.GREEN + "Druga pozycja zaznaczona na " + TextFormat.BLUE + b.x + TextFormat.GREEN + ", " + TextFormat.BLUE + b.y + TextFormat.GREEN + ", " + TextFormat.BLUE + b.z + TextFormat.GREEN);
                    break;
            }
        }
    }



}
