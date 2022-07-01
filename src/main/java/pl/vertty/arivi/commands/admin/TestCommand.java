package pl.vertty.arivi.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import org.apache.commons.lang3.StringUtils;
import pl.vertty.arivi.MainConstants;
import pl.vertty.arivi.commands.builder.Command;
import pl.vertty.arivi.commands.builder.PlayerCommand;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.utils.ChatUtil;

public class TestCommand extends PlayerCommand
{

    public TestCommand() {
        super("test", "Ustawianie test test", "/test", GroupType.ADMIN, new String[] { "" });
    }
    
    @Override
    public boolean onCommand(final Player sender, final String[] args) {
        final Item kilof633 = new Item(278, 0, 1).setCustomName(ChatUtil.fixColor("&r&9Kilof 6/3/3"));
        kilof633.addEnchantment(Enchantment.get(15).setLevel(6, false));
        kilof633.addEnchantment(Enchantment.get(18).setLevel(3, false));
        kilof633.addEnchantment(Enchantment.get(17).setLevel(3, false));
        sender.getInventory().addItem(kilof633);
        if(args[0].isEmpty()){
            return false;
        }
        MainConstants.fake = Integer.valueOf(args[0]);
        return false;
    }
    

}
