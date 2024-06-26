
package pl.vertty.arivi.gui.user;

import cn.nukkit.Player;
import pl.vertty.arivi.inventory.InventoryCategory;
import pl.vertty.arivi.inventory.InventoryMenu;
import pl.vertty.arivi.inventory.InventoryMenuHandler;
import pl.vertty.arivi.utils.ChatUtil;

public class SmietnikGui
{
    public static void openSmietnik(final Player player) {
        final InventoryMenu menu = new InventoryMenu();
        final InventoryCategory category = new InventoryCategory();
        menu.setMainCategory(category);
        menu.addCategory("SmietnikMenu", category);
        menu.setName(ChatUtil.fixColor("&9SMIETNIK"));
        menu.setOnlyRead(false);
        menu.show(player);
        InventoryMenuHandler.registerNewMenu("SmietnikMenu", menu);
    }
}
