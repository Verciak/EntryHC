package pl.vertty.arivi.gui.home;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.DyeColor;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.objects.guild.Guild;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.managers.guild.GuildManager;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import pl.vertty.arivi.utils.ChatUtil;
import pl.vertty.arivi.utils.TimerUtil;

public class HomeInventory
{
    public static void openHome(final Player player) {
        final User u = UserManager.getUser(player);
        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();

        final Item home1 = new Item(351, u.getHome1().contains("0.0:0.0:0.0:0.0:0.0") ? DyeColor.LIME.getDyeData() : DyeColor.RED.getDyeData(), 1);
        home1.setCustomName(ChatUtil.fixColor(u.getHome1().contains("0.0:0.0:0.0:0.0:0.0") ? "&9HOME 1 &8(&fKliknij, aby stworzyc!&8)" : "&9HOME 1"));
        home1.setLore(u.getHome1().contains("0.0:0.0:0.0:0.0:0.0") ? new String[] { ""} : new String[] { ChatUtil.fixColor("&8» &7Lokalizacja twojego domu:"), ChatUtil.fixColor("&8» &7X: &e" + u.getHome1Location().getX()), ChatUtil.fixColor("&8» &7Y: &e" + u.getHome1Location().getY()), ChatUtil.fixColor("&8» &7Z: &e" + u.getHome1Location().getZ()), "", ChatUtil.fixColor("&8» &7Kliknij, aby przeteleportowac sie na home!")});


        final Item home2 = new Item(351, u.getHome2().contains("0.0:0.0:0.0:0.0:0.0") ? DyeColor.LIME.getDyeData() : DyeColor.RED.getDyeData(), 1);
        home2.setCustomName(ChatUtil.fixColor(u.getHome2().contains("0.0:0.0:0.0:0.0:0.0") ? "&9HOME 2 &8(&fKliknij, aby stworzyc!&8)" : "&9HOME 2"));
        home2.setLore(u.getHome2().contains("0.0:0.0:0.0:0.0:0.0") ? new String[] { ""} : new String[] { ChatUtil.fixColor("&8» &7Lokalizacja twojego domu:"), ChatUtil.fixColor("&8» &7X: &e" + u.getHome2Location().getX()), ChatUtil.fixColor("&8» &7Y: &e" + u.getHome2Location().getY()), ChatUtil.fixColor("&8» &7Z: &e" + u.getHome2Location().getZ()), "", ChatUtil.fixColor("&8» &7Kliknij, aby przeteleportowac sie na home!")});

        final Item home3 = new Item(351, u.getHome3().contains("0.0:0.0:0.0:0.0:0.0") ? DyeColor.LIME.getDyeData() : DyeColor.RED.getDyeData(), 1);
        home3.setCustomName(ChatUtil.fixColor(u.getHome3().contains("0.0:0.0:0.0:0.0:0.0") ? "&9HOME 3 &8(&fKliknij, aby stworzyc!&8)" : "&9HOME 3"));
        home3.setLore(u.getHome3().contains("0.0:0.0:0.0:0.0:0.0") ? new String[] { ""} : new String[] { ChatUtil.fixColor("&8» &7Lokalizacja twojego domu:"), ChatUtil.fixColor("&8» &7X: &e" + u.getHome3Location().getX()), ChatUtil.fixColor("&8» &7Y: &e" + u.getHome3Location().getY()), ChatUtil.fixColor("&8» &7Z: &e" + u.getHome3Location().getZ()), "", ChatUtil.fixColor("&8» &7Kliknij, aby przeteleportowac sie na home!")});

        final Item home4 = new Item(351, u.getHome4().contains("0.0:0.0:0.0:0.0:0.0") ? DyeColor.LIME.getDyeData() : DyeColor.RED.getDyeData(), 1);
        home4.setCustomName(ChatUtil.fixColor(u.getHome4().contains("0.0:0.0:0.0:0.0:0.0") ? "&9HOME 4 &8(&fKliknij, aby stworzyc!&8)" : "&9HOME 4"));
        home4.setLore(u.getHome4().contains("0.0:0.0:0.0:0.0:0.0") ? new String[] { ""} : new String[] { ChatUtil.fixColor("&8» &7Lokalizacja twojego domu:"), ChatUtil.fixColor("&8» &7X: &e" + u.getHome4Location().getX()), ChatUtil.fixColor("&8» &7Y: &e" + u.getHome4Location().getY()), ChatUtil.fixColor("&8» &7Z: &e" + u.getHome4Location().getZ()), "", ChatUtil.fixColor("&8» &7Kliknij, aby przeteleportowac sie na home!")});

        final Item home5 = new Item(351, u.getHome5().contains("0.0:0.0:0.0:0.0:0.0") ? DyeColor.LIME.getDyeData() : DyeColor.RED.getDyeData(), 1);
        home5.setCustomName(ChatUtil.fixColor(u.getHome5().contains("0.0:0.0:0.0:0.0:0.0") ? "&9HOME 5 &8(&fKliknij, aby stworzyc!&8)" : "&9HOME 5"));
        home5.setLore(u.getHome5().contains("0.0:0.0:0.0:0.0:0.0") ? new String[] { ""} : new String[] { ChatUtil.fixColor("&8» &7Lokalizacja twojego domu:"), ChatUtil.fixColor("&8» &7X: &e" + u.getHome5Location().getX()), ChatUtil.fixColor("&8» &7Y: &e" + u.getHome5Location().getY()), ChatUtil.fixColor("&8» &7Z: &e" + u.getHome5Location().getZ()), "", ChatUtil.fixColor("&8» &7Kliknij, aby przeteleportowac sie na home!")});


        if (u.getHome1().contains("0.0:0.0:0.0:0.0:0.0")) {
            category.addElement(11, ItemData.fromItem(home1), new ItemClick() {
                @Override
                public void onClick(final Player p, final Item item) {
                    u.setHome1(p.getLocation());
                    ChatUtil.sendTitle(p, "&9HOME", "&6Pomyslnie ustawiono dom!");
                    HomeInventory.openHome(p);
                }
            });
        }
        else {
            category.addElement(11, ItemData.fromItem(home1), new ItemClick() {
                @Override
                public void onClick(final Player p, final Item item) {
                    final Guild g = GuildManager.getGuild(u.getHome1Location());
                    if (g != null && !g.isMember(p) && !u.can(GroupType.ADMIN)) {
                        ChatUtil.sendMessage(p, "&8>> &cDom jest na terenie wrogiej gildii!");
                        return;
                    }
                    p.getServer().getScheduler().scheduleDelayedTask(() -> TimerUtil.teleportSpawn(p, u.getHome1Location(), 10), 19);
                }
            });
        }
        if (u.getHome2().contains("0.0:0.0:0.0:0.0:0.0")) {
            category.addElement(12, ItemData.fromItem(home2), new ItemClick() {
                @Override
                public void onClick(final Player p, final Item item) {
                    u.setHome2(p.getLocation());
                    ChatUtil.sendTitle(p, "&9HOME", "&6Pomyslnie ustawiono dom!");
                    HomeInventory.openHome(p);
                }
            });
        }
        else {
            category.addElement(12, ItemData.fromItem(home2), new ItemClick() {
                @Override
                public void onClick(final Player p, final Item item) {
                    final Guild g = GuildManager.getGuild(u.getHome2Location());
                    if (g != null && !g.isMember(p) && !u.can(GroupType.ADMIN)) {
                        ChatUtil.sendMessage(p, "&8>> &cDom jest na terenie wrogiej gildii!");
                        return;
                    }
                    p.getServer().getScheduler().scheduleDelayedTask(() -> TimerUtil.teleportSpawn(p, u.getHome2Location(), 10), 19);
                }
            });
        }
        if (u.can(GroupType.VIP)) {
            if (u.getHome3().contains("0.0:0.0:0.0:0.0:0.0")) {
                category.addElement(13, ItemData.fromItem(home3), new ItemClick() {
                    @Override
                    public void onClick(final Player p, final Item item) {
                        u.setHome3(p.getLocation());
                        ChatUtil.sendTitle(p, "&9HOME", "&6Pomyslnie ustawiono dom!");
                        HomeInventory.openHome(p);
                    }
                });
            }
            else {
                category.addElement(13, ItemData.fromItem(home3), new ItemClick() {
                    @Override
                    public void onClick(final Player p, final Item item) {
                        final Guild g = GuildManager.getGuild(u.getHome3Location());
                        if (g != null && !g.isMember(p) && !u.can(GroupType.ADMIN)) {
                            ChatUtil.sendMessage(p, "&8>> &cDom jest na terenie wrogiej gildii!");
                            return;
                        }
                        p.getServer().getScheduler().scheduleDelayedTask(() -> TimerUtil.teleportSpawn(p, u.getHome3Location(), 10), 19);
                    }
                });
            }
        }
        if (u.can(GroupType.SVIP)) {
            if (u.getHome4().contains("0.0:0.0:0.0:0.0:0.0")) {
                category.addElement(14, ItemData.fromItem(home4), new ItemClick() {
                    @Override
                    public void onClick(final Player p, final Item item) {
                        u.setHome4(p.getLocation());
                        ChatUtil.sendTitle(p, "&9HOME", "&6Pomyslnie ustawiono dom!");
                        HomeInventory.openHome(p);
                    }
                });
            }
            else {
                category.addElement(14, ItemData.fromItem(home4), new ItemClick() {
                    @Override
                    public void onClick(final Player p, final Item item) {
                        final Guild g = GuildManager.getGuild(u.getHome4Location());
                        if (g != null && !g.isMember(p) && !u.can(GroupType.ADMIN)) {
                            ChatUtil.sendMessage(p, "&8>> &cDom jest na terenie wrogiej gildii!");
                            return;
                        }
                        p.getServer().getScheduler().scheduleDelayedTask(() -> TimerUtil.teleportSpawn(p, u.getHome4Location(), 10), 19);
                    }
                });
            }
            if (u.getHome5().contains("0.0:0.0:0.0:0.0:0.0")) {
                category.addElement(15, ItemData.fromItem(home5), new ItemClick() {
                    @Override
                    public void onClick(final Player p, final Item item) {
                        u.setHome5(p.getLocation());
                        ChatUtil.sendTitle(p, "&9HOME", "&6Pomyslnie ustawiono dom!");
                        HomeInventory.openHome(p);
                    }
                });
            }
            else {
                category.addElement(15, ItemData.fromItem(home5), new ItemClick() {
                    @Override
                    public void onClick(final Player p, final Item item) {
                        final Guild g = GuildManager.getGuild(u.getHome5Location());
                        if (g != null && !g.isMember(p) && !u.can(GroupType.ADMIN)) {
                            ChatUtil.sendMessage(p, "&8>> &cDom jest na terenie wrogiej gildii!");
                            return;
                        }
                        p.getServer().getScheduler().scheduleDelayedTask(() -> TimerUtil.teleportSpawn(p, u.getHome5Location(), 10), 19);
                    }
                });
            }
        }
        menu.setMainCategory(category);
        menu.addCategory("HomeInventory", category);
        menu.setName(ChatUtil.fixColor("&9HOME"));
        menu.setOnlyRead(true);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("HomeInventory", menu);
    }
}
