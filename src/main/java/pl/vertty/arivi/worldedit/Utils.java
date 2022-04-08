package pl.vertty.arivi.worldedit;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

import java.util.regex.Pattern;

public class Utils {

    public static Block fromString(String str) {
        String[] b = str.trim().replace(' ', '_').replace("minecraft:", "").split(":");

        int id = 0;
        int meta = 0;

        Pattern integerPattern = Pattern.compile("^[1-9]\\d*$");
        if (integerPattern.matcher(b[0]).matches()) {
            id = Integer.parseInt(b[0]);
        } else {
            try {
                id = Item.class.getField(b[0].toUpperCase()).getInt(null);
            } catch (Exception ignore) {
            }
        }

        id = id & 0xFFFF;
        if (b.length != 1) meta = Integer.parseInt(b[1]) & 0xFFFF;

        return Block.get(id, meta);
    }
}
