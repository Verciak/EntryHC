package pl.vertty.arivi.gui.adminpanel;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.MainConstants;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.inventory.item.ItemClick;
import pl.vertty.arivi.inventory.item.ItemData;
import pl.vertty.arivi.utils.ChatUtil;

public class AdminPanelGui
{
    public static void open(final Player player) {
        final Config c = Main.getPlugin().getConfig();

        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();
        category.addElement(0, ItemData.fromItem(MainConstants.PANDORA_ITEM_ADMINPANEL), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(c.getBoolean("enable.pandora.status") == true){
                    c.set("enable.pandora.status", false);
                    c.save();
                    MainConstants.set();
                    open(player);
                }else{
                    c.set("enable.pandora.status", true);
                    c.save();
                    MainConstants.set();
                    open(player);
                }
            }
        });
        category.addElement(1, ItemData.fromItem(MainConstants.KITS_ADMINPANEL), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(c.getBoolean("enable.kits.status") == true){
                    c.set("enable.kits.status", false);
                    c.save();
                    MainConstants.set();
                    open(player);
                }else{
                    c.set("enable.kits.status", true);
                    c.save();
                    MainConstants.set();
                    open(player);
                }
            }
        });
        category.addElement(2, ItemData.fromItem(MainConstants.ENCHANT_ADMINPANEL), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(c.getBoolean("enable.enchant.status") == true){
                    c.set("enable.enchant.status", false);
                    c.save();
                    MainConstants.set();
                    open(player);
                }else{
                    c.set("enable.enchant.status", true);
                    c.save();
                    MainConstants.set();
                    open(player);
                }
            }
        });
        category.addElement(3, ItemData.fromItem(MainConstants.GUILDS_ADMINPANEL), new ItemClick() {
            @Override
            public void onClick(final Player player, final Item item) {
                if(c.getBoolean("enable.guilds.status") == true){
                    c.set("enable.guilds.status", false);
                    c.save();
                    MainConstants.set();
                    open(player);
                }else{
                    c.set("enable.guilds.status", true);
                    c.save();
                    MainConstants.set();
                    open(player);
                }
            }
        });

        menu.setMainCategory(category);
        menu.addCategory("AdminPanelGUI", category);
        menu.setName(ChatUtil.fixColor("&9ADMINPANEL"));
        menu.setOnlyRead(true);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("AdminPanelGUI", menu);
    }
}
