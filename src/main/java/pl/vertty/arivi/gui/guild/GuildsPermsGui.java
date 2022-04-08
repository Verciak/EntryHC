
package pl.vertty.arivi.gui.guild;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import pl.vertty.arivi.managers.RoleManager;
import pl.vertty.arivi.managers.TablistManager;
import pl.vertty.arivi.objects.Role;
import pl.vertty.arivi.utils.ChatUtil;

public class GuildsPermsGui
{
    public static void openInv(final Player player, final String user) {
        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();
        category.setDoubleGuildRolesGui();
        Role role = RoleManager.getUser(user);


        category.addElement(11, ItemData.fromItem(new Item(1).setCustomName(ChatUtil.fixColor("&r&9Stawianie Kamienia")).setLore(ChatUtil.fixColor(ChatUtil.fixColor("&r&8» &7Status: ") + (role.isUpr_Place_Stone() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Place_Stone(!role.isUpr_Place_Stone());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(12, ItemData.fromItem(new Item(1).setCustomName(ChatUtil.fixColor("&r&9Niszczenie Kamienia")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_Break_Stone() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Break_Stone(!role.isUpr_Break_Stone());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(13, ItemData.fromItem(new Item(49).setCustomName(ChatUtil.fixColor("&r&9Stawianie Obsydianu")).setLore(ChatUtil.fixColor(ChatUtil.fixColor("&r&8» &7Status: ") + (role.isUpr_Place_Obsidian() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Place_Obsidian(!role.isUpr_Place_Obsidian());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(14, ItemData.fromItem(new Item(49).setCustomName(ChatUtil.fixColor("&r&9Niszczenie Obsidianu")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_Break_Obsidian() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Break_Obsidian(!role.isUpr_Break_Obsidian());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(15, ItemData.fromItem(new Item(54).setCustomName(ChatUtil.fixColor("&r&9Uzywanie skrzynek")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_Chest() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Chest(!role.isUpr_Chest());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(19, ItemData.fromItem(new Item(61).setCustomName(ChatUtil.fixColor("&r&9Otwieranie piecy")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_Furnace() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Furnace(!role.isUpr_Furnace());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(20, ItemData.fromItem(new Item(22).setCustomName(ChatUtil.fixColor("&r&9Dostep do lapisu")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_Lapis() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Lapis(!role.isUpr_Lapis());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(21, ItemData.fromItem(new Item(46).setCustomName(ChatUtil.fixColor("&r&9Uzywanie tnt")).setLore(ChatUtil.fixColor(ChatUtil.fixColor("&r&8» &7Status: ") + (role.isUpr_Tnt() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Tnt(!role.isUpr_Tnt());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(22, ItemData.fromItem(new Item(120).setCustomName(ChatUtil.fixColor("&r&9Uzywanie farmerow")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_Boyfarmer() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Boyfarmer(!role.isUpr_Boyfarmer());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(23, ItemData.fromItem(new Item(8).setCustomName(ChatUtil.fixColor("&r&9Rozlewanie wody")).setLore(ChatUtil.fixColor(ChatUtil.fixColor("&r&8» &7Status: ") + (role.isUpr_Water() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Water(!role.isUpr_Water());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(24, ItemData.fromItem(new Item(10).setCustomName(ChatUtil.fixColor("&r&9Rozlewanie lawy")).setLore(ChatUtil.fixColor(ChatUtil.fixColor("&r&8» &7Status: ") + (role.isUpr_Lava() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Lava(!role.isUpr_Lava());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(25, ItemData.fromItem(new Item(ItemID.ENDER_EYE).setCustomName(ChatUtil.fixColor("&r&9Dostep do uprawnien")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_perms() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_perms(!role.isUpr_perms());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(29, ItemData.fromItem(new Item(ItemID.SKULL, 3, 1).setCustomName(ChatUtil.fixColor("&r&9Dodawanie do gildii")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_addMember() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_addMember(!role.isUpr_addMember());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(30, ItemData.fromItem(new Item(ItemID.SKULL, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Wyrzucanie z gildii")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_removeMember() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_removeMember(!role.isUpr_removeMember());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(31, ItemData.fromItem(new Item(ItemID.FEATHER, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Teleportacja na dom gildii")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_base_teleport() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_base_teleport(!role.isUpr_base_teleport());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(32, ItemData.fromItem(new Item(ItemID.MAGMA_CREAM, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Zmiana pvp w gildii")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_pvpguild() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_pvpguild(!role.isUpr_pvpguild());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(33, ItemData.fromItem(new Item(ItemID.FIRE_CHARGE, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Zmiana pvp w sojuszu")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_pvpally() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_pvpally(!role.isUpr_pvpally());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(38, ItemData.fromItem(new Item(BlockID.DIAMOND_BLOCK, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Wyplacanie z skarbca")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_withdrawchest() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_withdrawchest(!role.isUpr_withdrawchest());
                GuildsPermsGui.openInv(player, user);
            }
        });

        category.addElement(42, ItemData.fromItem(new Item(BlockID.ENDER_CHEST, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Dostep do skrzynki gildii")).setLore(ChatUtil.fixColor("&r&8» &7Status: " + (role.isUpr_guildchest() ? "&aTak" : "&cNie")), ChatUtil.fixColor("&r&8» &7Kliknij aby zmienic"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_guildchest(!role.isUpr_guildchest());
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(48, ItemData.fromItem(new Item(340).setCustomName(ChatUtil.fixColor("&r&9Wlacz wszystkie")).setLore(ChatUtil.fixColor("&r&8» &7Kliknij aby wlaczyc uprawnienia!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Furnace(true);
                role.setUpr_perms(true);
                role.setUpr_Lava(true);
                role.setUpr_Boyfarmer(true);
                role.setUpr_Chest(true);
                role.setUpr_Tnt(true);
                role.setUpr_Place_Obsidian(true);
                role.setUpr_Place_Stone(true);
                role.setUpr_Break_Obsidian(true);
                role.setUpr_Break_Stone(true);
                role.setUpr_Lapis(true);
                role.setUpr_Water(true);
                role.setUpr_addMember(true);
                role.setUpr_removeMember(true);
                role.setUpr_base_teleport(true);
                role.setUpr_pvpguild(true);
                role.setUpr_pvpally(true);
                role.setUpr_withdrawchest(true);
                role.setUpr_guildchest(true);
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(50, ItemData.fromItem(new Item(340).setCustomName(ChatUtil.fixColor("&r&9Wylacz wszystkie")).setLore(ChatUtil.fixColor("&r&8» &7Kliknij aby wylaczyc uprawnienia!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                role.setUpr_Furnace(false);
                role.setUpr_perms(false);
                role.setUpr_Lava(false);
                role.setUpr_Boyfarmer(false);
                role.setUpr_Chest(false);
                role.setUpr_Tnt(false);
                role.setUpr_Place_Obsidian(false);
                role.setUpr_Place_Stone(false);
                role.setUpr_Break_Obsidian(false);
                role.setUpr_Lapis(false);
                role.setUpr_Break_Stone(false);
                role.setUpr_Water(false);
                role.setUpr_addMember(false);
                role.setUpr_removeMember(false);
                role.setUpr_base_teleport(false);
                role.setUpr_pvpguild(false);
                role.setUpr_pvpally(false);
                role.setUpr_withdrawchest(false);
                role.setUpr_guildchest(false);
                GuildsPermsGui.openInv(player, user);
            }
        });
        category.addElement(49, ItemData.fromItem(new Item(ItemID.NETHER_STAR).setCustomName(ChatUtil.fixColor("&r&9Wroc")).setLore(ChatUtil.fixColor("&r&8» &7Kliknij aby wrocic!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                GuildPanelGui.openInv(player);
            }
        });
        menu.setDoubleChest();
        menu.setMainCategory(category);
        menu.addCategory("Menu" + role.getName(), category);
        menu.setName(pl.vertty.arivi.utils.ChatUtil.fixColor("&9Edytowanie uprawnien: &9" + role.getName()));
        menu.setOnlyRead(true);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("Menu" + role.getName(), menu);
    }
}
