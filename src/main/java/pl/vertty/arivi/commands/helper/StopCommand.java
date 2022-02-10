// 
// Decompiled by Procyon v0.5.36
// 

package pl.vertty.arivi.commands.helper;

import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.commands.builder.Command;
import pl.vertty.arivi.enums.GroupType;
import pl.vertty.arivi.commands.builder.PlayerCommand;
import pl.vertty.arivi.managers.RestartManager;
import pl.vertty.arivi.utils.ChatUtil;

import java.util.Timer;
import java.util.TimerTask;

public class StopCommand extends Command
{
    public StopCommand() {
        super("stop", "Zatrzymywanie serwera", "/stop", GroupType.HELPER, new String[] { "" });
    }
    
    @Override
    public boolean onExecute(final CommandSender p, final String[] args) {
        Timer timer = new Timer();
        timer.schedule(new App(), 0, 1000);
        RestartManager.deleteWhitelistStatus(0);
        RestartManager.addWhiteListStatus(1);
        return true;
    }
}

class App extends TimerTask {

    int countdown = 10;

    public void run() {
        countdown = countdown - 1;
        for(Player p : Server.getInstance().getOnlinePlayers().values()){
            ChatUtil.sendTitleRestart(p, "","&cRestart serwera nastapi za: &4"+ countdown+" sekund!");
        }
        if(countdown <= 0){
            Main.onRestart();
        }
    }

}