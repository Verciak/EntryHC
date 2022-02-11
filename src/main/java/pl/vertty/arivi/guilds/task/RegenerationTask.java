
package pl.vertty.arivi.guilds.task;

import cn.nukkit.block.Block;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.NukkitRunnable;
import pl.vertty.arivi.guilds.data.block.BlockRegeneration;
import pl.vertty.arivi.guilds.data.guild.Guild;
import pl.vertty.arivi.guilds.data.yml.Config;
import pl.vertty.arivi.guilds.utils.ChatUtil;

import java.util.List;

public class RegenerationTask extends NukkitRunnable
{
    private final List<BlockRegeneration> blocks;
    private final Guild g;
    
    public void run() {
        if (this.blocks.size() > 0) {
            final BlockRegeneration blockRegeneration = this.blocks.get(0);
            if (blockRegeneration != null) {
                final Location location = blockRegeneration.getLocation();
                location.getLevel().setBlock(new Vector3((double)location.getFloorX(), (double)location.getFloorY(), (double)location.getFloorZ()), Block.get(blockRegeneration.getIdBlock()));
            }
            this.blocks.remove(blockRegeneration);
            if (this.blocks.isEmpty()) {
                this.g.title(ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE4));
                this.g.setRegeneration(false);
                this.cancel();
            }
            if (this.g.getSkarbiec() < 1) {
                this.g.title(ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_TITLE), ChatUtil.fixColor(Config.GUILD_COMMAND_REGENERATION_SUBTITLE3.replace("{X}", Integer.toString(this.blocks.size()))));
                this.g.setRegeneration(false);
                this.cancel();
            }
        }
    }
    
    public RegenerationTask(final Guild g, final List<BlockRegeneration> blocks) {
        this.g = g;
        this.blocks = blocks;
    }
}
