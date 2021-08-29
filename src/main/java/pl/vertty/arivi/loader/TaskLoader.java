// 
// Decompiled by Procyon v0.5.36
// 

package pl.vertty.arivi.loader;

import pl.vertty.arivi.managers.VanishManager;
import cn.nukkit.Server;
import pl.vertty.arivi.task.ItemsClearTask;
import pl.vertty.arivi.task.LimitTask;
import pl.vertty.arivi.task.AutoMessageTask;
import pl.vertty.arivi.task.CombatTask;
import pl.vertty.arivi.guilds.task.ActionBarTask;
import pl.vertty.arivi.guilds.utils.TimeUtil;
import pl.vertty.arivi.guilds.task.AutoSaveTask;
import pl.vertty.arivi.guilds.task.CheckValidityTask;
import cn.nukkit.plugin.Plugin;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.guilds.task.NameTagUpdateTask;

public class TaskLoader
{
    public static void onTaskLoad() {
        new NameTagUpdateTask().runTaskTimer((Plugin)Main.getPlugin(), 20, 20);
        new CheckValidityTask().runTaskTimer((Plugin)Main.getPlugin(), 2400, 2400);
        new AutoSaveTask().runTaskTimer((Plugin)Main.getPlugin(), TimeUtil.MINUTE.getTick(1), TimeUtil.MINUTE.getTick(1));
        new ActionBarTask().runTaskTimerAsynchronously((Plugin)Main.getPlugin(), 40, 20);
        new CombatTask().runTaskTimer((Plugin)Main.getPlugin(), 40, 20);
        new AutoMessageTask().runTaskTimer((Plugin)Main.getPlugin(), 600, 600);
        new LimitTask().runTaskTimer((Plugin)Main.getPlugin(), 100, 200);
        Server.getInstance().getScheduler().scheduleDelayedRepeatingTask((Plugin)Main.getPlugin(), (Runnable)new VanishManager(), 20, 20);
        new ItemsClearTask().runTaskTimer((Plugin)Main.getPlugin(), 20, 2400);
    }
}
