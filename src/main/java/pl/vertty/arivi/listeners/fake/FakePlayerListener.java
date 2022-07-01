package pl.vertty.arivi.listeners.fake;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.QueryRegenerateEvent;
import pl.vertty.arivi.MainConstants;

public class FakePlayerListener implements Listener {

    @EventHandler
    public void onFake(QueryRegenerateEvent e){
        e.setPlayerCount(e.getPlayerCount() + MainConstants.fake);
    }

}
