
package pl.vertty.arivi.gui.guild;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.utils.DyeColor;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.objects.User;
import pl.vertty.arivi.objects.guild.Guild;
import pl.vertty.arivi.managers.UserManager;
import pl.vertty.arivi.managers.guild.GuildManager;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import pl.vertty.arivi.task.SandFarmerTask;
import pl.vertty.arivi.utils.ChatUtil;

public class GuildSandFarmerGui
{
    public static void openTopki(final Player player) {
        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();
        final User user = UserManager.getUser(player);
        Guild g = GuildManager.getGuild(player);
        category.setDoubleGuildMembersGui();


        category.addElement(11, new ItemData(Item.WOOL, DyeColor.LIME.getWoolData(), 1, "&r&9Dodaj 1 kratke", new String[]{"", "&r&8>> &7Kliknij, aby dodac kratki!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(user.kratki >= g.getRegion().getSize()){
                    ChatUtil.sendMessage(p0, "&cNie mozesz dodac juz kratek");
                    menu.forceDestroy(p0);
                    return;
                }
                user.kratki = user.kratki + 1;
                if(user.kratki >= g.getRegion().getSize()){
                    user.kratki = g.getRegion().getSize();
                }
                GuildSandFarmerGui.openTopki(p0);
            }
        });
        category.addElement(20, new ItemData(Item.WOOL, DyeColor.LIME.getWoolData(), 1, "&r&9Dodaj 2 kratki", new String[]{"", "&r&8>> &7Kliknij, aby dodac kratki!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(user.kratki >= g.getRegion().getSize()){
                    ChatUtil.sendMessage(p0, "&cNie mozesz dodac juz kratek");
                    menu.forceDestroy(p0);
                    return;
                }
                user.kratki = user.kratki + 3;
                if(user.kratki >= g.getRegion().getSize()){
                    user.kratki = g.getRegion().getSize();
                }
                GuildSandFarmerGui.openTopki(p0);
            }
        });
        category.addElement(29, new ItemData(Item.WOOL, DyeColor.LIME.getWoolData(), 1, "&r&9Dodaj 5 kratek", new String[]{"", "&r&8>> &7Kliknij, aby dodac kratki!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(user.kratki >= g.getRegion().getSize()){
                    ChatUtil.sendMessage(p0, "&cNie mozesz dodac juz kratek");
                    menu.forceDestroy(p0);
                    return;
                }
                user.kratki = user.kratki + 5;
                if(user.kratki >= g.getRegion().getSize()){
                    user.kratki = g.getRegion().getSize();
                }
                GuildSandFarmerGui.openTopki(p0);
            }
        });
        category.addElement(15, new ItemData(Item.WOOL, DyeColor.RED.getWoolData(), 1, "&r&9Odejmij 1 kratke", new String[]{"", "&r&8>> &7Kliknij, aby odjac kratki!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(user.kratki <= 5){
                    ChatUtil.sendMessage(p0, "&cNie mozesz odjac juz kratek");
                    menu.forceDestroy(p0);
                    return;
                }
                user.kratki = user.kratki - 1;
                if(user.kratki <= 5){
                    user.kratki = 5;
                }
                GuildSandFarmerGui.openTopki(p0);
            }
        });
        category.addElement(24, new ItemData(Item.WOOL, DyeColor.RED.getWoolData(), 1, "&r&9Odejmij 2 kratki", new String[]{"", "&r&8>> &7Kliknij, aby odjac kratki!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(user.kratki <= 5){
                    ChatUtil.sendMessage(p0, "&cNie mozesz odjac juz kratek");
                    menu.forceDestroy(p0);
                    return;
                }
                user.kratki = user.kratki - 2;
                if(user.kratki <= 5){
                    user.kratki = 5;
                }
                GuildSandFarmerGui.openTopki(p0);
            }
        });
        category.addElement(33, new ItemData(Item.WOOL, DyeColor.RED.getWoolData(), 1, "&r&9Odejmij 5 kratek", new String[]{"", "&r&8>> &7Kliknij, aby odjac kratki!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(user.kratki <= 5){
                    ChatUtil.sendMessage(p0, "&cNie mozesz odjac juz kratek");
                    menu.forceDestroy(p0);
                    return;
                }
                user.kratki = user.kratki - 5;
                if(user.kratki <= 5){
                    user.kratki = 5;
                }
                GuildSandFarmerGui.openTopki(p0);
            }
        });
        category.addElement(31, new ItemData(Item.END_CRYSTAL, 0, 1, "&r&9Rozpocznij tworzenie &fSandFarmera", new String[]{"", "&r&8>> &cSandFarmer tworzy sie na &4Y:70","&r&8>> &aKliknij, aby rozpoczac!"}), new ItemClick() {
            @Override
            public void onClick(Player p0, Item p1) {
                if(g.getSkarbiec() < (g.getRegion().getSize() * user.kratki / 2)){
                    ChatUtil.sendMessage(p0, "&cNie posiadasz &4" + (g.getRegion().getSize() * user.kratki / 2) + "&cemeraldow w skarbcu");
                    menu.forceDestroy(p0);
                    return;
                }
                g.setSkarbiec(g.getSkarbiec() -(g.getRegion().getSize() * user.kratki / 2));
                new SandFarmerTask(g, user.kratki).runTaskTimer(Main.getPlugin(), 5, 5);

            }
        });
        category.addElement(13, new ItemData(Item.BOOK, 0, 1, "&r&9Informacje", new String[]{"", "&r&8>> &7Twoj rozmiar gildii: &9" + g.getRegion().getSize(), "&r&8>> &7Aktualna ilosc kratek od srodka: &9" + user.kratki, "", "&r&8>> &7Koszt stworzenia &fSandFarmera&7:&9 " +(g.getRegion().getSize() * user.kratki / 2), "&r&8>> &7Aktualnie emeraldow w skrabcu:&9 " +g.getSkarbiec()}));

        category.addElement(49, ItemData.fromItem(new Item(ItemID.NETHER_STAR).setCustomName(pl.vertty.arivi.utils.guild.ChatUtil.fixColor("&r&9Wroc")).setLore(pl.vertty.arivi.utils.guild.ChatUtil.fixColor("&r&8» &7Kliknij aby wrocic!"))), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                GuildPanelGui.openInv(player);
            }
        });
        menu.setDoubleChest();
        menu.setMainCategory(category);
        menu.addCategory("GuildSandFarmerGui", category);
        menu.setName(ChatUtil.fixColor("&9Panel SandFarmerow"));
        menu.setOnlyRead(true);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("GuildSandFarmerGui", menu);
    }
}
