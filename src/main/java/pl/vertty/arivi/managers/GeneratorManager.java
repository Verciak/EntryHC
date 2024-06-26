
package pl.vertty.arivi.managers;

import pl.vertty.arivi.utils.guild.ChatUtil;
import pl.vertty.arivi.objects.yml.Config;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.plugin.Plugin;
import pl.vertty.arivi.Main;
import cn.nukkit.level.Level;
import pl.vertty.arivi.objects.Combat;
import cn.nukkit.math.Vector3;
import cn.nukkit.Server;
import pl.vertty.arivi.enums.TimeUtil;
import pl.vertty.arivi.objects.guild.Guild;
import cn.nukkit.block.Block;
import cn.nukkit.scheduler.NukkitRunnable;
import pl.vertty.arivi.managers.guild.GuildManager;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.Player;

public class GeneratorManager
{
    public static void repairGenerator(final BlockBreakEvent blockBreakEvent) {
        final Block block = blockBreakEvent.getBlock();
        final Guild guild = GuildManager.getGuild(block.getLocation());
        final Block block2 = blockBreakEvent.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getLevelBlock();
        final Block block1 = blockBreakEvent.getBlock().getLocation().subtract(0.0, 2.0, 0.0).getLevelBlock();
        if (block.getId() == 1) {
            new NukkitRunnable() {
                public void run() {
                    if (block1.getId() != 121) {
                        return;
                    }
                    if (guild != null && guild.isMember(blockBreakEvent.getPlayer().getName()) && guild.getLastExplodeTime() + TimeUtil.SECOND.getTime(120) > System.currentTimeMillis()) {
                        return;
                    }
                    final Combat combat = CombatManager.getCombat(blockBreakEvent.getPlayer());
                    if (guild != null && guild.isMember(blockBreakEvent.getPlayer().getName()) && combat != null && combat.hasFight()) {
                        return;
                    }
                    final Level l = Server.getInstance().getDefaultLevel();
                    l.setBlock(new Vector3(block1.getX(), block1.getY() + 2.0, block1.getZ()), Block.get(1));
                }
            }.runTaskLater(Main.getPlugin(), 35);
            new NukkitRunnable() {
                public void run() {
                    if (block2.getId() != 121) {
                        return;
                    }
                    if (guild != null && guild.isMember(blockBreakEvent.getPlayer().getName()) && guild.getLastExplodeTime() + TimeUtil.SECOND.getTime(120) > System.currentTimeMillis()) {
                        return;
                    }
                    final Combat combat = CombatManager.getCombat(blockBreakEvent.getPlayer());
                    if (guild != null && guild.isMember(blockBreakEvent.getPlayer().getName()) && combat != null && combat.hasFight()) {
                        return;
                    }
                    final Level l = Server.getInstance().getDefaultLevel();
                    l.setBlock(new Vector3(block2.getX(), block2.getY() + 1.0, block2.getZ()), Block.get(1));
                }
            }.runTaskLater(Main.getPlugin(), 35);
        }
    }
    
    public static void createGenerator(final Player player, final BlockPlaceEvent blockPlaceEvent) {
        final Block block = blockPlaceEvent.getBlock();
        final Guild guild = GuildManager.getGuild(block.getLocation());
        final Block block1 = blockPlaceEvent.getBlock().getLocation().add(0.0, 2.0, 0.0).getLevelBlock();
        final Block block2 = blockPlaceEvent.getBlock().getLocation().add(0.0, 1.0, 0.0).getLevelBlock();
        if (blockPlaceEvent.isCancelled()) {
            return;
        }
        if (block.getId() != 121) {
            return;
        }
        final Combat combat = CombatManager.getCombat(blockPlaceEvent.getPlayer());
        if (guild != null && guild.isMember(blockPlaceEvent.getPlayer().getName()) && combat != null && combat.hasFight()) {
            player.sendMessage(ChatUtil.fixColor(Config.GENERATOR_ERROR2));
            blockPlaceEvent.setCancelled(true);
            return;
        }
        if (guild != null && guild.isMember(blockPlaceEvent.getPlayer().getName()) && guild.getLastExplodeTime() + TimeUtil.SECOND.getTime(120) > System.currentTimeMillis()) {
            blockPlaceEvent.setCancelled(true);
            return;
        }
        final Level l = Server.getInstance().getDefaultLevel();
        l.setBlock(new Vector3(block2.getX(), block2.getY(), block2.getZ()), Block.get(1));
        l.setBlock(new Vector3(block1.getX(), block1.getY(), block1.getZ()), Block.get(1));

    }
}
