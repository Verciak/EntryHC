package pl.vertty.arivi.drop.system;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.drop.base.Drop;
import pl.vertty.arivi.drop.base.User;
import pl.vertty.arivi.drop.base.utils.DropUtils;
import pl.vertty.arivi.drop.utils.MathUtils;
import pl.vertty.arivi.drop.utils.RandomUtils;
import pl.vertty.arivi.drop.utils.StringUtils;
import pl.vertty.arivi.drop.utils.Util;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class DropCustom implements DropData
{
    @Override
    public void breakBlock(final Player player, final Item tool, final Block block, final User user) {
        final List<Item> drops = new ArrayList<>();
        final Config c = Main.getPlugin().getConfig();
        player.addExperience(user.isTurboExp() ? (c.getInt("exp.stone") + 15) : c.getInt("exp.stone") + 5);
        if (DropUtils.isCobble(player.getName())) {
            for (final Enchantment e : tool.getEnchantments()) {
                if (e.getId() == 16) {
                    final Item stone = Item.get(1, 0, 1);
                    drops.add(stone);
                }
                else {
                    final Item stone = Item.get(4, 0, 1);
                    drops.add(stone);
                }
            }
            user.addCobble();
        }
        for (final Drop drop : DropUtils.getDrops()) {
            if (drop.isDisabled(player.getName())) {
                continue;
            }
            final pl.vertty.arivi.objects.User ua = UserManager.getUser(player);
            double chance = ua.can(GroupType.VIP) ? ((user.isTurbo() || Util.isTurbo()) ? MathUtils.calcTurbo(drop.getChance() + 1.0) : (drop.getChance() + 1.0)) : ((user.isTurbo() || Util.isTurbo()) ? MathUtils.calcTurbo(drop.getChance()) : drop.getChance());
            if(Util.turbo_perla != 0L){
                if (drop.getItem().getId() == Item.ENDER_PEARL){
                    chance = chance + 2.0;
                }
            }
            if (!RandomUtils.getChance(chance)) {
                continue;
            }
            int amount = 1;
            if (drop.isFortune()) {
                for (final Enchantment e2 : tool.getEnchantments()) {
                    if (e2.getId() == 18) {
                        if (e2.getLevel() == 1) {
                            amount = RandomUtils.nextInt(2) + 1;
                        }
                        if (e2.getLevel() == 2) {
                            amount = RandomUtils.nextInt(3) + 1;
                        }
                        if (e2.getLevel() == 3) {
                            amount = RandomUtils.nextInt(4) + 1;
                        }
                    }
                    else {
                        amount = 1;
                    }
                }
            }
            final Item item = drop.getItem().clone();
            item.setCount(amount);
            drops.add(item);
            user.addDrop(drop, amount);
            if (DropUtils.isEq(player.getName())) {
                final PlayerInventory inventoryAutoAdd = player.getInventory();
                final Item[] itemsToAdd = { Item.get(item.getId(), 0, item.getCount()) };
                for (int i = 0; i < itemsToAdd.length; ++i) {
                    final boolean canAddItem = inventoryAutoAdd.canAddItem(itemsToAdd[i]);
                    if (canAddItem) {
                        inventoryAutoAdd.addItem(itemsToAdd[i]);
                    }
                    else {
                        if (drop.getItem().getId() == 368) {
                            final pl.vertty.arivi.objects.User u = UserManager.getUser(player);
                            u.addPerly(u.getPerly() + amount);
                            return;
                        }
                        player.dropItem(Item.get(drop.getItem().getId(), drop.getItem().getDamage(), amount));
                    }
                }
            }
            else {
                final Location loc = block.getLocation();
                final Item ia = new Item(drop.getItem().getId(), 0, amount);
                loc.getLevel().dropItem(block.getLocation(), ia);
            }
            if (!DropUtils.isMsg(player.getName())) {
                continue;
            }
            player.sendActionBar(StringUtils.replace(drop.getMessage(), "{AMOUNT}", Integer.toString(amount)));
        }
        final Location ca = block.getLocation();
        final Level l = Server.getInstance().getDefaultLevel();
        final double x = ca.getFloorX();
        final double y = ca.getFloorY();
        final double z = ca.getFloorZ();
        l.setBlock(new Vector3(x, y, z), Block.get(0));
    }
}
