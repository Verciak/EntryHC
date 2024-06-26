
package pl.vertty.arivi.loader;

import pl.vertty.arivi.task.*;
import pl.vertty.arivi.task.ActionBarTask;
import pl.vertty.arivi.task.AutoSaveTask;
import pl.vertty.arivi.task.CheckValidityTask;
import pl.vertty.arivi.Main;
import pl.vertty.arivi.task.NameTagUpdateTask;

public class TaskLoader
{
    public static void onTaskLoad() {
        new NameTagUpdateTask().runTaskTimer(Main.getPlugin(), 1000, 1000);
        new CheckValidityTask().runTaskTimer(Main.getPlugin(), 2400, 2400);
        new AutoSaveTask().runTaskTimer(Main.getPlugin(), 300, 300);
        new ActionBarTask().runTaskTimerAsynchronously(Main.getPlugin(), 40, 20);
        new CombatTask().runTaskTimer(Main.getPlugin(), 40, 20);
        new BossBarTask().runTaskTimer(Main.getPlugin(), 1, 1);
        new AutoMessageTask().runTaskTimer(Main.getPlugin(), 600, 600);
        new LimitTask().runTaskTimer(Main.getPlugin(), 100, 200);
        new ItemsClearTask().runTaskTimer(Main.getPlugin(), 20, 2400);
    }
}
