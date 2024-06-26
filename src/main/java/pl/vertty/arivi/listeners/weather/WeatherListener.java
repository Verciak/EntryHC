
package pl.vertty.arivi.listeners.weather;

import cn.nukkit.event.EventPriority;
import cn.nukkit.event.block.BlockBurnEvent;
import cn.nukkit.event.block.BlockFadeEvent;
import cn.nukkit.event.block.BlockFallEvent;
import cn.nukkit.event.block.LeavesDecayEvent;
import cn.nukkit.event.level.LevelLoadEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.Listener;

public class WeatherListener implements Listener
{
//    @EventHandler
//    public void weatherChange(final WeatherChangeEvent event) {
//        event.setCancelled(true);
//    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void loadworld(final LevelLoadEvent e) {
        e.getLevel().setRaining(false);
        e.getLevel().setThundering(false);
    }

}
