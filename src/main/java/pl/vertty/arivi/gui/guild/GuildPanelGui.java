
package pl.vertty.arivi.gui.guild;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.plugin.Plugin;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import pl.vertty.arivi.managers.RoleManager;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.managers.guild.GuildManager;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.objects.guild.Guild;
import pl.vertty.arivi.objects.yml.Config;
import pl.vertty.arivi.task.RegenerationTask;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.utils.DataUtil;
import pl.vertty.arivi.utils.exception.SkinChangeException;
import pl.vertty.arivi.utils.guild.TimeUtil;
import pl.vertty.arivi.utils.guild.itemstack.ItemStackUtil;

public class GuildPanelGui
{
    public static void openInv(final Player player) {
        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();
        final Guild guild = GuildManager.getGuild(player);
        final Guild guild2 = GuildManager.getGuild(player);
        category.setDoublePanelServerGUI();
        category.addElement(12, ItemData.fromItem(new Item(49, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Zarzadanie BoyFarmerami")).setLore(ChatUtil.fixColor("&r&8» &7Funkcja ktora pozwala zarzadzac &ffarmerami!"), ChatUtil.fixColor("&r&8» &7Kliknij aby otworzyc &fpanel BoyFarmerow!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(!RoleManager.getUser(player).isUpr_Boyfarmer()){
                    ChatUtil.sendMessage(player, "&4Nie posiadasz pozwolen do &czarzadnia farmerami");
                    menu.forceDestroy(player);
                    return;
                }
                GuildBoyFarmerGui.openTopki(player);
            }
        });
        category.addElement(13, ItemData.fromItem(new Item(1, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Zarzadanie Kopaczami")).setLore(ChatUtil.fixColor("&r&8» &7Funkcja ktora pozwala zarzadzac &ffarmerami!"), ChatUtil.fixColor("&r&8» &7Kliknij aby otworzyc &fpanel Kopaczy!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(!RoleManager.getUser(player).isUpr_Boyfarmer()){
                    ChatUtil.sendMessage(player, "&4Nie posiadasz pozwolen do &czarzadnia farmerami");
                    menu.forceDestroy(player);
                    return;
                }
                GuildKopaczFarmerGui.openTopki(player);
            }
        });
        category.addElement(14, ItemData.fromItem(new Item(12, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Zarzadanie SandFarmerami")).setLore(ChatUtil.fixColor("&r&8» &7Funkcja ktora pozwala zarzadzac &ffarmerami!"), ChatUtil.fixColor("&r&8» &7Kliknij aby otworzyc &fpanel SandFarmerow!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(!RoleManager.getUser(player).isUpr_Boyfarmer()){
                    ChatUtil.sendMessage(player, "&4Nie posiadasz pozwolen do &czarzadnia farmerami");
                    menu.forceDestroy(player);
                    return;
                }
                GuildSandFarmerGui.openTopki(player);
            }
        });
        category.addElement(21, ItemData.fromItem(Item.get(347, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Oplac gildie")).setLore(ChatUtil.fixColor("&r&8» &7Koszt: &9" + ItemStackUtil.getItem(Config.COST_PROLONG_NORMAL, 1)), ChatUtil.fixColor("&r&8» &7Kliknij aby oplacic gildie o &9" + Config.PROLONG_ADD + " &7dni"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if (guild2.getProlong() > System.currentTimeMillis() + TimeUtil.DAY.getTime(Config.PROLONG_MAX)) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_PROLONG_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_PROLONG_SUBTITLE2));
                    menu.forceDestroy(player);
                    return;
                }
                final String cost_PROLONG_NORMAL = Config.COST_PROLONG_NORMAL;
                if (!ItemStackUtil.checkItems(player, cost_PROLONG_NORMAL, 1)) {
                    ItemStackUtil.getItem(player, cost_PROLONG_NORMAL, 1);
                    menu.forceDestroy(player);
                    return;
                }
                menu.forceDestroy(player);
                ItemStackUtil.removeItems(player, cost_PROLONG_NORMAL, 1);
                guild2.setProlong(guild2.getProlong() + TimeUtil.DAY.getTime(Config.PROLONG_ADD));
                ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_PROLONG_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_PROLONG_SUBTITLE.replace("{PROLONG}", Integer.toString(Config.PROLONG_ADD))));
            }
        });
        category.addElement(23, ItemData.fromItem(new Item(345, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Powieksz teren gildi")).setLore(ChatUtil.fixColor("&r&8» &7Koszt: &9" + ItemStackUtil.getItem(Config.COST_ENLARGE_NORMAL, 1)), ChatUtil.fixColor("&r&8» &7Kliknij aby powiekszyc teren o &9" + Config.CUBOID_SIZE_ADD + " &7kratek"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if (guild2.getRegion().getSize() >= Config.CUBOID_SIZE_MAX) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_ENLARGE_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_ENLARGE_SUBTITLE2));
                    menu.forceDestroy(player);
                    return;
                }
                final int n = (guild2.getRegion().getSize() - Config.CUBOID_SIZE_START) / 5 + 1;
                final String cost_ENLARGE_NORMAL = Config.COST_ENLARGE_NORMAL;
                if (!ItemStackUtil.checkItems(player, cost_ENLARGE_NORMAL, n)) {
                    ItemStackUtil.getItem(player, cost_ENLARGE_NORMAL, n);
                    menu.forceDestroy(player);
                    return;
                }
                menu.forceDestroy(player);
                ItemStackUtil.removeItems(player, cost_ENLARGE_NORMAL, n);
                guild2.addSize(Config.CUBOID_SIZE_ADD);
                final int i = guild2.getRegion().getSize() + 1;
                ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_ENLARGE_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_ENLARGE_SUBTITLE.replace("{SIZE}", Integer.toString(i))));
            }
        });
        category.addElement(29, ItemData.fromItem(new Item(ItemID.FIRE_CHARGE,0,1).setCustomName(ChatUtil.fixColor("&r&9Powieksz limit sojuszy")).setLore(ChatUtil.fixColor("&r&8» &7Koszt: &930 &7emeraldow"), ChatUtil.fixColor("&r&8» &7Kliknij aby powiekszyc limit sojuszy o &91"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if (guild2.getSkarbiec() < 30) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_SOJUSZ_TITLE), ChatUtil.fixColor("&cNie posiadasz {HEAD} emeraldow!".replace("{HEAD}", "30")));
                    player.getInventory().onClose(player);
                    return;
                }
                if (guild2.getSojusz() >= 4) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_SOJUSZ_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_SOJUSZ_SUBTITLE2));
                    menu.forceDestroy(player);
                    return;
                }
                menu.forceDestroy(player);
                guild2.setSojusz(guild2.getSojusz() + 1);
                guild2.setSkarbiec(guild2.getSkarbiec() - 30);
                ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_SOJUSZ_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_SOJUSZ_SUBTITLE));
            }
        });
        category.addElement(49, ItemData.fromItem(new Item(323, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Informacje o gildii!")).setLore(ChatUtil.fixColor("&r&8» &7Tag: &9" + guild.getTag()), ChatUtil.fixColor("&r&8» &7Nazwa: &9" + guild.getName()), ChatUtil.fixColor("&r&8» &7Glowy: &9" + guild.getHead()), ChatUtil.fixColor("&r&8» &7HP: &9" + guild.getHp()), ChatUtil.fixColor("&r&8» &7Zycia: &9" + guild.getLife()), ChatUtil.fixColor("&r&8» &7Limit Sojuszy: &9" + guild.getSojusz()), ChatUtil.fixColor("&r&8» &7Limit Graczy: &9" + guild.getLimitMembers()), ChatUtil.fixColor("&r&8» &7Rozmiar: &9" + guild.getRegion().getSize() + "&7x&9" + guild.getRegion().getSize()), ChatUtil.fixColor("&r&8» &7Wygasa za: &9" + DataUtil.secondsToString(guild.getProlong())))));
        category.addElement(31, ItemData.fromItem(new Item(46, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Regeneracja terenu")).setLore(ChatUtil.fixColor("&r&8» &7Do zregenerowania: &9" + guild.getBlocks().size() + " &7blokow"), ChatUtil.fixColor("&r&8» &7Koszt regeneracji: &9" + guild.getBlocks().size() / 2 + " &7emeraldow"), ChatUtil.fixColor(" "), ChatUtil.fixColor("&r&8» &7Regeneracja nie dziala na: "), ChatUtil.fixColor("  &r&8- &9Tnt"), ChatUtil.fixColor("  &r&8- &9Dzwignie"), ChatUtil.fixColor("  &r&8- &9Wagoniki"), ChatUtil.fixColor("  &r&8- &9Pistony"), ChatUtil.fixColor("  &r&8- &9Repetery"), ChatUtil.fixColor(" "), ChatUtil.fixColor("&r&8» &7Kliknij aby zregenerowac!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if (!guild2.isOwner(player.getName())) {
                    player.sendMessage(ChatUtil.fixColor(Config.GUILD_NOT_OWNER));
                    menu.forceDestroy(player);
                    return;
                }
                if (!TimeUtil.isTnt()) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE1));
                    menu.forceDestroy(player);
                    return;
                }
                if (guild2.getBlocks().isEmpty()) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE));
                    menu.forceDestroy(player);
                    return;
                }
                if (guild2.isRegeneration()) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE8));
                    menu.forceDestroy(player);
                    return;
                }
                if (guild2.getSkarbiec() < 1) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE2));
                    menu.forceDestroy(player);
                    return;
                }
                menu.forceDestroy(player);
                guild2.setRegeneration(true);
                final int koszt = guild2.getBlocks().size() / 2;
                guild2.setSkarbiec(guild2.getSkarbiec() - koszt);
                new RegenerationTask(guild2, guild2.getBlocks()).runTaskTimer((Plugin)Main.getPlugin(), 0, 1);
                ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE5));
            }
        });
        category.addElement(33, ItemData.fromItem(new Item(ItemID.NAME_TAG, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Powieksz limit czlonkow")).setLore(ChatUtil.fixColor("&r&8» &7Koszt: &930 &7emeraldow"), ChatUtil.fixColor("&r&8» &7Kliknij aby powiekszyc limit czlonkow o &910"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if (guild2.getSkarbiec() < 30) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_MEMBER_TITLE), ChatUtil.fixColor("&cNie posiadasz {HEAD} emeraldow!".replace("{HEAD}", "30")));
                    menu.forceDestroy(player);
                    return;
                }
                if (guild2.getLimitMembers() > 99) {
                    ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_MEMBER_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_MEMBER_SUBTITLE2));
                    menu.forceDestroy(player);
                    return;
                }
                menu.forceDestroy(player);
                guild2.setLimitMembers(guild2.getLimitMembers() + 10);
                guild2.setSkarbiec(guild2.getSkarbiec() - 30);
                ChatUtil.sendTitle(player, ChatUtil.fixColor(Config.GUILD_PANEL_MEMBER_TITLE), ChatUtil.fixColor(Config.GUILD_PANEL_MEMBER_SUBTITLE));
            }
        });
        category.addElement(40, ItemData.fromItem(new Item(ItemID.SKULL, 3, 1).setCustomName(ChatUtil.fixColor("&r&9Zarzadzaj uprawnieniami")).setLore(ChatUtil.fixColor("&r&8» &7Kliknij aby zarzadzac uprawnieniami czlonkow"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                final Guild guild = GuildManager.getGuild(player);
                if (guild == null) {
                    player.sendMessage(ChatUtil.fixColor(Config.GUILD_NOT_GUILD));
                    menu.forceDestroy(player);
                    return;
                }
                if (!RoleManager.getUser(player).isUpr_perms()) {
                    player.sendMessage(ChatUtil.fixColor("&cNie posiadasz pozwolen od lidera!"));
                    menu.forceDestroy(player);
                    return;
                }
                GuildMembersGUI.openMembers(player);
            }
        });
        menu.setDoubleChest();
        menu.setMainCategory(category);
        menu.addCategory("gpanel" + player.getName(), category);
        menu.setName(pl.vertty.arivi.utils.ChatUtil.fixColor("&9Panel Gilidyjny"));
        menu.setOnlyRead(true);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("gpanel" + player.getName(), menu);
    }
}
