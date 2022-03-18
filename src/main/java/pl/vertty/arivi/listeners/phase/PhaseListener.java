package pl.vertty.arivi.listeners.phase;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFence;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Faceable;
import pl.vertty.arivi.Cooldown;
import pl.vertty.arivi.drop.utils.Util;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.utils.ChatUtil;

public class PhaseListener implements Listener {


    @EventHandler(ignoreCancelled = false)
    public void onPhase2(PlayerMoveEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user.can(GroupType.HELPER))
            return;
        if (e.getTo().getY() < 0.0D) {
            e.getPlayer().teleport(Util.getHighestLocation(e.getFrom()));
            return;
        }
        if (e.getFrom().clone().setComponents(e.getFrom().getX(), 0.0D, e.getFrom().getZ()).distance((Vector3) e.getTo().clone().setComponents(e.getTo().getX(), 0.0D, e.getTo().getZ())) > 3.0D) {
            e.getPlayer().teleport(e.getFrom());
            if (!Cooldown.getInstance().has(e.getPlayer(), "phase1")) {
                for (Player paa : Server.getInstance().getOnlinePlayers().values()) {
                    User ua = UserManager.getUser(paa);
                    if (ua.can(GroupType.HELPER)) {
                        ChatUtil.sendMessage(paa, "&9AC &8> &7Gracz &3" + e.getPlayer().getName() + " &8(&7ruch byl zbyt odlegly&8) &8(&f" + e.getPlayer().getPing() + "ms&8)!");
                        Cooldown.getInstance().add(e.getPlayer(), "phase1", 2.0F);
                    }
                }
            }
        } else if (e.getFrom().getFloorX() != e.getTo().getFloorX() || e.getFrom().getFloorZ() != e.getTo().getFloorZ() || e.getFrom().getFloorY() != e.getTo().getFloorY()) {
            if (inBlock(e.getFrom()) && inBlock(e.getTo())) {
                e.getPlayer().teleport(e.getFrom());
                if (!Cooldown.getInstance().has(e.getPlayer(), "phase2")) {
                    for (Player paa : Server.getInstance().getOnlinePlayers().values()) {
                        User ua = UserManager.getUser(paa);
                        if (ua.can(GroupType.HELPER)) {
                            ChatUtil.sendMessage(paa, "&9AC &8> &7Gracz &3" + e.getPlayer().getName() + " &8(&7probuje chodzic za sciana, kiedy jest w scianie&8) &8(&f" + e.getPlayer().getPing() + "ms&8)!");
                            Cooldown.getInstance().add(e.getPlayer(), "phase2", 2.0F);
                        }
                    }
                }
                return;
            }
            if (!inBlock(e.getFrom()) && inBlock(e.getTo())) {
                e.getPlayer().teleport(e.getFrom());
                if (!Cooldown.getInstance().has(e.getPlayer(), "phase3")) {
                    for (Player paa : Server.getInstance().getOnlinePlayers().values()) {
                        User ua = UserManager.getUser(paa);
                        if (ua.can(GroupType.HELPER)) {
                            ChatUtil.sendMessage(paa, "&9AC &8> &7Gracz &3" + e.getPlayer().getName() + " &8(&7probuje chodzic za sciana&8) &8(&f" + e.getPlayer().getPing() + "ms&8)!");
                            Cooldown.getInstance().add(e.getPlayer(), "phase3", 2.0F);
                        }
                    }
                }
            }
        }
    }

    private boolean inBlock(Location loc) {
        Block b1 = Util.getBlock(loc.getLevel(), loc.getFloorX(), loc.getFloorY(), loc.getFloorZ(), false);
        Location lup = loc.getSide(BlockFace.UP).getLocation();
        Block b2 = Util.getBlock(loc.getLevel(), lup.getFloorX(), lup.getFloorY(), lup.getFloorZ(), false);
        return (b1.isNormalBlock() && b2.isNormalBlock() && b1.getId() != 0 && b2.getId() != 0 && !(b1 instanceof cn.nukkit.block.BlockFence) && !(b2 instanceof cn.nukkit.block.BlockFence) && !(b1 instanceof cn.nukkit.utils.Faceable) && !(b2 instanceof cn.nukkit.utils.Faceable) &&

                !b2.canPassThrough() && !b1.canPassThrough());
    }
}
