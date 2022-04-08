package pl.vertty.arivi.gui.guild;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import pl.vertty.arivi.managers.TablistManager;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.managers.guild.GuildManager;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.objects.guild.Guild;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.utils.exception.SkinChangeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuildMembersGUI {


    public static void openMembers(final Player player) {
        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();
        category.setDoubleGuildMembersGui();

        final Guild g = GuildManager.getGuild(player);
        for (int i = 0; i < Objects.requireNonNull(g).getMembers().size(); ++i) {
            final List<String> list = new ArrayList<>(g.getMembers());
            final User member = UserManager.getUser(list.get(i));
            final Item ib = new Item(397, 3, 1).setCustomName(ChatUtil.fixColor("&r&9" + member.getName())).setLore(ChatUtil.fixColor("&r&8» &7Nacisnij aby zarzadzac"));
            category.addElementAir(ItemData.fromItem(ib), new ItemClick() {
                @Override
                public void onClick(final Player player, final Item item) {
                    final Guild guild = GuildManager.getGuild(player);
                    for (int j = 0; j < Objects.requireNonNull(guild).getMembers().size(); ++j) {
                        final List<String> list = new ArrayList<>(guild.getMembers());
                        final User member2 = UserManager.getUser(list.get(j));
                        if (item.getCustomName().equals(ChatUtil.fixColor("&r&9" + guild.getOwner()))) {
                            pl.vertty.arivi.utils.guild.ChatUtil.sendSubTitle(player, "&cNie mozesz edytowac permisji lidera!");
                            menu.forceDestroy(player);
                            return;
                        }
                        if (item.getCustomName().equals(ChatUtil.fixColor("&r&9" + member2.getName()))) {
                            GuildsPermsGui.openInv(player, member2.getName());
                        }
                    }
                }
            }, 54);
        }

        category.addElement(49, ItemData.fromItem(new Item(ItemID.NETHER_STAR).setCustomName(pl.vertty.arivi.utils.guild.ChatUtil.fixColor("&r&9Wroc")).setLore(pl.vertty.arivi.utils.guild.ChatUtil.fixColor("&r&8» &7Kliknij aby wrocic!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                GuildPanelGui.openInv(player);
            }
        });
        menu.setDoubleChest();
        menu.setMainCategory(category);
        menu.addCategory("GuildMembersGui" + player.getName(), category);
        menu.setName(ChatUtil.fixColor("&9Zarzadzanie czlonkami"));
        menu.setOnlyRead(true);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("GuildMembersGui" + player.getName(), menu);
    }

}
