
package pl.vertty.arivi.listeners.player;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.player.*;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import pl.vertty.arivi.MainConstants;
import pl.vertty.arivi.drop.utils.RandomUtils;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.DataPacket;

import java.io.IOException;
import java.util.Iterator;

import cn.nukkit.event.EventPriority;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.objects.Role;
import pl.vertty.arivi.managers.RoleManager;
import pl.vertty.arivi.managers.ACManager;
import pl.vertty.arivi.objects.ItemShop;
import pl.vertty.arivi.managers.ItemShopManager;
import pl.vertty.arivi.utils.exception.SkinChangeException;
import pl.vertty.arivi.utils.SkinUtil;
import cn.nukkit.event.EventHandler;
import pl.vertty.arivi.objects.Combat;
import cn.nukkit.Player;
import pl.vertty.arivi.managers.CombatManager;
import pl.vertty.arivi.drop.base.User;
import pl.vertty.arivi.drop.base.utils.UserUtils;
import pl.vertty.arivi.wings.WingsManager;
import pl.vertty.arivi.wings.mysql.UserWings;
import pl.vertty.arivi.managers.UserManager;
import cn.nukkit.event.Listener;

public class PlayerJoinListener implements Listener
{


    @EventHandler(ignoreCancelled = true)
    public void onChangeSkin(PlayerChangeSkinEvent e) {
        Player p = e.getPlayer();
        Skin newSkin = e.getSkin();
        if (newSkin == null || newSkin.isPersona() || !newSkin.isValid()) {
            e.setCancelled();
            return;
        }
    }

    @EventHandler
    public void onCreate(final PlayerLoginEvent e) throws IOException {
        final Player p = e.getPlayer();
        final pl.vertty.arivi.objects.User u = UserManager.getUser(p);
        Role role = RoleManager.getUser(p);
        ItemShop is = ItemShopManager.getUser(p);
        p.setGamemode(0);
        if(UserWings.getUser(p) != null){
            if(UserWings.getUser(p).getWings() != null){
                WingsManager.setRatWings(p, WingsManager.getWings(UserWings.getUser(p).getWings()));
            }
        }
        if(ACManager.getUser(p) == null){
            ACManager.createrUser(p);
        }
        if (!UserUtils.playedBefore(p.getName())) {
            new User(p.getName());
        }
        if (role == null) {
            RoleManager.createrUser(p);
        }
        if (u == null) {
            UserManager.createrUser(p);
        }
        if (is == null) {
            ItemShopManager.createrUser(p);
        }
        final Combat combat = CombatManager.getCombat(p);
        if (combat == null) {
            CombatManager.createCombat(p);
        }
        if(u != null){
            if(!u.can(GroupType.HELPER)) {
                p.setOp(false);
            }
        }

    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onLogin(final PlayerLoginEvent event) throws SkinChangeException {
        final Player p = event.getPlayer();
        p.setGamemode(0);
        p.setCheckMovement(false);
        final pl.vertty.arivi.objects.User u = UserManager.getUser(p);
        SkinUtil.originalSkins.put(event.getPlayer().getUniqueId(), event.getPlayer().getSkin());
        if(u != null){
            if(!u.can(GroupType.HELPER)) {
                p.setOp(false);
            }
        }
        if (u != null && u.isIncognitoSkin()) {
            SkinUtil.changeSkin(p);
        }
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Combat c = CombatManager.getCombat(e.getPlayer());
        Iterator<Player> iterator = Server.getInstance().getOnlinePlayers().values().iterator();
        if (iterator.hasNext()) {
            Player p = iterator.next();
            Combat ca = CombatManager.getCombat(e.getPlayer());
            if (ca == null) {
                CombatManager.createCombat(p);
            }
            return;
        }
        if (c == null) {
            CombatManager.createCombat(e.getPlayer());
        }
        e.setJoinMessage("");
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        e.setQuitMessage("");
    }
    
    @EventHandler
    public void onDataPacket(final DataPacketReceiveEvent e) {
        final DataPacket data = e.getPacket();
        final Player p = e.getPlayer();
        Combat ca = CombatManager.getCombat(e.getPlayer());
        if (ca == null) {
            CombatManager.createCombat(p);
        }
        if (data instanceof SetLocalPlayerAsInitializedPacket && !p.hasPlayedBefore()) {
            p.getInventory().addItem(Item.get(274, 0, 1));
            p.getInventory().addItem(Item.get(320, 0, 64));
            p.getInventory().addItem(Item.get(130, 0, 1));
            final int x = RandomUtils.getRandInt(-MainConstants.BORDER, MainConstants.BORDER);
            final int z = RandomUtils.getRandInt(-MainConstants.BORDER, MainConstants.BORDER);
            final Location location = p.getLocation();
            final Block tele = p.getLocation().getLevel().getBlock(new Vector3(location.getX(), location.getY(), location.getZ()));
            final Location telep = tele.getLocation();
            final Location loc = new Location((double)x, (double)telep.getLevel().getHighestBlockAt(x, z), (double)z);
            p.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }
}
