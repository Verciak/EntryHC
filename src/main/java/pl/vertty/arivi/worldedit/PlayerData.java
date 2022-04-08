package pl.vertty.arivi.worldedit;

import cn.nukkit.Player;
import lombok.Getter;

public class PlayerData {

    @Getter
    private final Player player;

    @Getter
    private final Selection selection;

    @Getter
    public BlocksCopy copiedBlocks = null;

    public PlayerData(Player p) {
        this.player = p;
        selection = new Selection();
    }
}
